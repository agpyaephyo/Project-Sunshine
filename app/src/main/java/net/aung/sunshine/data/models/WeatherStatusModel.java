package net.aung.sunshine.data.models;

import android.os.AsyncTask;

import net.aung.sunshine.data.responses.WeatherStatusListResponse;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.events.DataEvent;
import net.aung.sunshine.network.WeatherDataSource;
import net.aung.sunshine.network.WeatherDataSourceImpl;
import net.aung.sunshine.utils.CommonInstances;
import net.aung.sunshine.utils.JsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
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
        WeatherStatusListResponse weatherStatusListResponse = weatherStatusListResponseMap.get(city);
        if (weatherStatusListResponse == null || isForce) {
            weatherDataSource.getWeatherForecastList(city, LOADING_TYPE_LIST);
            return new ArrayList<>();
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
        WeatherStatusListResponse weatherStatusListResponse = weatherStatusListResponseMap.get(currentCity);
        if (weatherStatusListResponse == null) {
            weatherDataSource.getWeatherForecastList(currentCity, LOADING_TYPE_DETAIL);
            return null;
        } else {
            return findWeatherStatusByDate(weatherStatusListResponse, dateForWeatherDetail);
        }
    }

    private WeatherStatusVO findWeatherStatusByDate(WeatherStatusListResponse weatherStatusListResponse, long dateForWeatherDetail) {
        ArrayList<WeatherStatusVO> weatherStatusList = weatherStatusListResponse.getWeatherStatusList();
        for(WeatherStatusVO weatherStatus : weatherStatusList) {
            if (weatherStatus.getDateTime() == dateForWeatherDetail)
                return weatherStatus;
        }

        return null;
    }

    public void onEventMainThread(DataEvent.LoadedWeatherStatusListEvent event) {
        WeatherStatusListResponse response = event.getResponse();
        String city = response.getCity().getName();
        weatherStatusListResponseMap.put(city, response);

        if(event.getLoadingType() == LOADING_TYPE_LIST) {
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
}
