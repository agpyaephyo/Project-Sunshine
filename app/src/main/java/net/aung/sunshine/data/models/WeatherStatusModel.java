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

    private static WeatherStatusModel objInstance;

    private WeatherDataSource weatherDataSource;
    private Map<String, WeatherStatusListResponse> weatherStatusListResponseMap;

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

    public List<WeatherStatusVO> loadWeatherStatusList(String city) {
        WeatherStatusListResponse weatherStatusListResponse = weatherStatusListResponseMap.get(city);
        if (weatherStatusListResponse == null) {
            weatherDataSource.getWeatherForecastList(city);
            return new ArrayList<>();
        } else {
            return weatherStatusListResponse.getWeatherStatusList();
        }
    }

    public void onEventMainThread(DataEvent.LoadedWeatherStatusListEvent event) {
        WeatherStatusListResponse response = event.getResponse();
        String city = response.getCity().getName();
        weatherStatusListResponseMap.put(city, response);

        DataEvent.Loaded14DaysWeatherEvent eventToUI = new DataEvent.Loaded14DaysWeatherEvent(response.getWeatherStatusList());
        EventBus.getDefault().post(eventToUI);
    }

    public List<WeatherStatusVO> loadDummyWeatherStatusList(String city) {
        WeatherStatusListResponse weatherStatusListResponse = weatherStatusListResponseMap.get(city);
        if (weatherStatusListResponse == null) {
            new LoadDummyWeatherStatusListTask().execute(city);
            return new ArrayList<>();
        } else {
            return weatherStatusListResponse.getWeatherStatusList();
        }
    }

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

            DataEvent.Loaded14DaysWeatherEvent event = new DataEvent.Loaded14DaysWeatherEvent(weatherStatusListResponseMap.get(city).getWeatherStatusList());
            EventBus.getDefault().post(event);
        }
    }
}
