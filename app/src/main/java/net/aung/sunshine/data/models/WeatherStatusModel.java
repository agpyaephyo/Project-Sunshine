package net.aung.sunshine.data.models;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import net.aung.sunshine.SunshineApplication;
import net.aung.sunshine.data.persistence.WeatherContract;
import net.aung.sunshine.data.responses.WeatherStatusListResponse;
import net.aung.sunshine.data.vos.CityVO;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.events.DataEvent;
import net.aung.sunshine.network.WeatherDataSource;
import net.aung.sunshine.network.WeatherDataSourceImpl;

import de.greenrobot.event.EventBus;

/**
 * Created by aung on 12/10/15.
 */
public class WeatherStatusModel {

    private static WeatherStatusModel objInstance;

    private WeatherDataSource weatherDataSource;

    public static WeatherStatusModel getInstance() {
        if (objInstance == null) {
            objInstance = new WeatherStatusModel();
        }

        return objInstance;
    }

    private WeatherStatusModel() {
        weatherDataSource = WeatherDataSourceImpl.getInstance();

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    public void loadWeatherStatusList(String city, boolean isForce) {
        if (isForce) {
            weatherDataSource.getWeatherForecastList(city);
        }
    }

    public void onEventMainThread(DataEvent.LoadedWeatherStatusListEvent event) {
        WeatherStatusListResponse response = event.getResponse();
        cacheWeatherData(response);

        DataEvent.RefreshNewWeatherDataEvent eventToUI = new DataEvent.RefreshNewWeatherDataEvent();
        EventBus.getDefault().post(eventToUI);
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
}
