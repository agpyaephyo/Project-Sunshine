package net.aung.sunshine.mvp.presenters;

import android.util.Log;

import net.aung.sunshine.SunshineApplication;
import net.aung.sunshine.data.models.WeatherStatusModel;
import net.aung.sunshine.events.DataEvent;
import net.aung.sunshine.mvp.views.ForecastListView;
import net.aung.sunshine.utils.SettingsUtils;

/**
 * Created by aung on 12/14/15.
 */
public class ForecastListPresenter extends BasePresenter {

    private ForecastListView forecastListView;

    public ForecastListPresenter(ForecastListView forecastListView) {
        this.forecastListView = forecastListView;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void onEventMainThread(DataEvent.RefreshNewWeatherDataEvent event) {
        forecastListView.refreshNewWeatherData();
    }

    public void onEventMainThread(DataEvent.LoadedWeatherStatusListErrorEvent event) {
        String errorMessage = event.getError();
        forecastListView.displayErrorMessage(errorMessage);
    }

    public void forceRefresh() {
        String userLocation = SettingsUtils.retrieveUserCity();
        Log.d(SunshineApplication.TAG, "Force refresh weather data for city : " + userLocation);

        WeatherStatusModel.getInstance().loadWeatherStatusList(userLocation, true);
    }

}
