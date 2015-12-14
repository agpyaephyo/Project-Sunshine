package net.aung.sunshine.data.models;

import android.os.AsyncTask;
import android.util.SparseArray;

import net.aung.sunshine.data.responses.WeatherStatusListResponse;
import net.aung.sunshine.data.vos.DailyWeatherStatusVO;
import net.aung.sunshine.events.DataEvent;
import net.aung.sunshine.utils.CommonInstances;
import net.aung.sunshine.utils.JsonUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by aung on 12/10/15.
 */
public class DailyWeatherStatusModel {

    private static final String DUMMY_WEATHER_DATA_FILENAME = "singapore_14days_weather.json";

    private static DailyWeatherStatusModel objInstance;

    private SparseArray<WeatherStatusListResponse> weatherStatusSparseArray; //14 days weather statuses based on city id.

    public static DailyWeatherStatusModel getInstance() {
        if (objInstance == null) {
            objInstance = new DailyWeatherStatusModel();
        }

        return objInstance;
    }

    private DailyWeatherStatusModel() {
        weatherStatusSparseArray = new SparseArray<>();
    }

    public List<DailyWeatherStatusVO> load14daysWeather(int cityID) {
        WeatherStatusListResponse weatherStatusListResponse = weatherStatusSparseArray.get(cityID);
        if (weatherStatusListResponse == null) {
            new Load14DaysWeatherStatusTask().execute(cityID);
            return null;
        } else {
            return weatherStatusListResponse.getWeatherStatusList();
        }
    }

    private class Load14DaysWeatherStatusTask extends AsyncTask<Integer, Void, WeatherStatusListResponse> {

        private int cityID;

        @Override
        protected WeatherStatusListResponse doInBackground(Integer... params) {
            cityID = params[0];
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
            weatherStatusSparseArray.put(cityID, response);

            DataEvent.Loaded14DaysWeatherEvent event = new DataEvent.Loaded14DaysWeatherEvent(weatherStatusSparseArray.get(cityID).getWeatherStatusList());
            EventBus.getDefault().post(event);
        }
    }
}
