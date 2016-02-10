package net.aung.sunshine.data.models;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import net.aung.sunshine.SunshineApplication;
import net.aung.sunshine.data.persistence.WeatherContract;
import net.aung.sunshine.data.responses.WeatherStatusListResponse;
import net.aung.sunshine.data.vos.CityVO;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.events.DataEvent;
import net.aung.sunshine.network.WeatherDataSource;
import net.aung.sunshine.network.WeatherDataSourceImpl;
import net.aung.sunshine.utils.CommonInstances;
import net.aung.sunshine.utils.JsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by aung on 12/10/15.
 */
public class WeatherStatusModel {

    private static final String DUMMY_WEATHER_DATA_FILENAME = "singapore_14days_weather.json";
    public static final int LOADING_TYPE_LIST = 1;
    public static final int LOADING_TYPE_DETAIL = 2;

    private static WeatherStatusModel objInstance;

    private WeatherDataSource weatherDataSource;
    private Map<String, WeatherStatusListResponse> weatherStatusListResponseMap;

    private String currentCity;
    private long currentDateForWeatherDetail;

    public static WeatherStatusModel getInstance() {
        if (objInstance == null) {
            objInstance = new WeatherStatusModel();
        }

        return objInstance;
    }

    private WeatherStatusModel() {
        weatherStatusListResponseMap = new HashMap<>();
        weatherDataSource = WeatherDataSourceImpl.getInstance();

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    public List<WeatherStatusVO> loadWeatherStatusList(String city, boolean isForce) {
        currentCity = city;
        String key = getKeyForWeatherStatus(city);
        WeatherStatusListResponse weatherStatusListResponse = weatherStatusListResponseMap.get(key);
        if (weatherStatusListResponse == null || isForce) {
            weatherDataSource.getWeatherForecastList(city, LOADING_TYPE_LIST);

            //TODO load from db.
            WeatherStatusListResponse cacheResposne = loadWeatherDataFromCache(city);
            weatherStatusListResponseMap.put(key, cacheResposne);
            return cacheResposne.getWeatherStatusList();
        } else {
            return weatherStatusListResponse.getWeatherStatusList();
        }
    }

    //just to test the data layer before network layer hooks up to the api.
    public List<WeatherStatusVO> loadDummyWeatherStatusList(String city) {
        currentCity = city;
        WeatherStatusListResponse weatherStatusListResponse = weatherStatusListResponseMap.get(city);
        if (weatherStatusListResponse == null) {
            new LoadDummyWeatherStatusListTask().execute(city);
            return new ArrayList<>();
        } else {
            return weatherStatusListResponse.getWeatherStatusList();
        }
    }

    public WeatherStatusVO loadWeatherStatusDetail(long dateForWeatherDetail) {
        currentDateForWeatherDetail = dateForWeatherDetail;
        String key = getKeyForWeatherStatus(currentCity);
        WeatherStatusListResponse weatherStatusListResponse = weatherStatusListResponseMap.get(key);
        if (weatherStatusListResponse == null) {
            weatherDataSource.getWeatherForecastList(currentCity, LOADING_TYPE_DETAIL);
            return null;
        } else {
            return findWeatherStatusByDate(weatherStatusListResponse, dateForWeatherDetail);
        }
    }

    private WeatherStatusVO findWeatherStatusByDate(WeatherStatusListResponse weatherStatusListResponse, long dateForWeatherDetail) {
        ArrayList<WeatherStatusVO> weatherStatusList = weatherStatusListResponse.getWeatherStatusList();
        for (WeatherStatusVO weatherStatus : weatherStatusList) {
            if (weatherStatus.getDateTime() == dateForWeatherDetail)
                return weatherStatus;
        }

        return null;
    }

    public void onEventMainThread(DataEvent.LoadedWeatherStatusListEvent event) {
        WeatherStatusListResponse response = event.getResponse();
        String cityName = response.getCity().getName();
        String key = getKeyForWeatherStatus(cityName);
        weatherStatusListResponseMap.put(key, response);

        cacheWeatherData(response);

        if (event.getLoadingType() == LOADING_TYPE_LIST) {
            DataEvent.NewWeatherStatusList eventToUI = new DataEvent.NewWeatherStatusList(response.getWeatherStatusList());
            EventBus.getDefault().post(eventToUI);
        } else if (event.getLoadingType() == LOADING_TYPE_DETAIL) {
            WeatherStatusVO weatherStatus = findWeatherStatusByDate(response, currentDateForWeatherDetail);
            DataEvent.NewWeatherStatusDetail eventToUI = new DataEvent.NewWeatherStatusDetail(weatherStatus);
            EventBus.getDefault().post(eventToUI);
        }
    }

    //just to test the data layer before network layer hooks up to the api.
    private class LoadDummyWeatherStatusListTask extends AsyncTask<String, Void, WeatherStatusListResponse> {

        private String city;

        @Override
        protected WeatherStatusListResponse doInBackground(String... params) {
            city = params[0];
            WeatherStatusListResponse response = null;

            try {
                String dummy14DaysWeatherList = JsonUtils.getInstance().loadDummyData(DUMMY_WEATHER_DATA_FILENAME);
                response = CommonInstances.getGsonInstance().fromJson(dummy14DaysWeatherList, WeatherStatusListResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(WeatherStatusListResponse response) {
            super.onPostExecute(response);
            weatherStatusListResponseMap.put(city, response);

            DataEvent.NewWeatherStatusList event = new DataEvent.NewWeatherStatusList(weatherStatusListResponseMap.get(city).getWeatherStatusList());
            EventBus.getDefault().post(event);
        }
    }

    /**
     * Dummy key generation for weather status hash map.
     *
     * @param city
     * @return
     */
    private String getKeyForWeatherStatus(String city) {
        return city;
    }

    private void cacheWeatherData(WeatherStatusListResponse response) {
        CityVO cityVO = response.getCity();
        ContentValues cityCV = cityVO.getContentValues();
        Context context = SunshineApplication.getContext();

        Cursor cursorCity = context.getContentResolver().query(WeatherContract.CityEntry.CONTENT_URI,
                null,
                WeatherContract.CityEntry.COLUMN_CITY_NAME + " = ?",
                new String[]{cityVO.getName()},
                null);

        long cityRowId;
        if (!cursorCity.moveToFirst()) {
            //no city with this name yet. proceed to insert.
            Uri insertedCityUri = context.getContentResolver().insert(WeatherContract.CityEntry.CONTENT_URI, cityCV);
            cityRowId = ContentUris.parseId(insertedCityUri);
        } else {
            cityRowId = cursorCity.getLong(cursorCity.getColumnIndex(WeatherContract.CityEntry._ID));
        }
        cursorCity.close();

        ContentValues[] weatherCVArray = WeatherStatusVO.parseToContentValuesArray(response.getWeatherStatusList(), cityRowId);
        context.getContentResolver().bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI, weatherCVArray);
    }

    private WeatherStatusListResponse loadWeatherDataFromCache(String city) {
        ArrayList<WeatherStatusVO> weatherStatusList = new ArrayList<>();

        Context context = SunshineApplication.getContext();
        long today = new Date().getTime() / 1000 - (24 * 60 * 60); //to make sure we are showing for today also.
        Cursor cursorWeather = context.getContentResolver().query(WeatherContract.WeatherEntry.buildWeatherUriWithStartDate(city, today),
                null, null, null, WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry.COLUMN_DATE + " ASC");

        if (cursorWeather.moveToFirst()) {
            do {
                weatherStatusList.add(WeatherStatusVO.parseFromCursor(cursorWeather));
            } while (cursorWeather.moveToNext());

            Cursor cursorCity = context.getContentResolver().query(WeatherContract.CityEntry.CONTENT_URI,
                    null,
                    WeatherContract.CityEntry.COLUMN_CITY_NAME + " = ?",
                    new String[]{city},
                    null);

            if(cursorCity.moveToFirst()) {
                CityVO cachedCity = CityVO.parseFromCursor(cursorCity);
                return WeatherStatusListResponse.createFromCache(weatherStatusList, cachedCity);
            }

            cursorCity.close();
        }
        cursorWeather.close();

        return WeatherStatusListResponse.createFromCache(new ArrayList<WeatherStatusVO>(), null);
    }
}
