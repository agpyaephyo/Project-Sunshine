package net.aung.sunshine.mvp.presenters;

import net.aung.sunshine.data.models.WeatherStatusModel;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.events.DataEvent;
import net.aung.sunshine.mvp.views.ForecastListView;

import java.util.List;

/**
 * Created by aung on 12/14/15.
 */
public class ForecastListPresenter extends BasePresenter {

    private static final String DUMMY_CITY_NAME = "Singapore"; //ABCDEFG

    private ForecastListView forecastListView;

    public ForecastListPresenter(ForecastListView forecastListView) {
        this.forecastListView = forecastListView;
    }

    @Override
    public void onStart() {
        List<WeatherStatusVO> weatherStatusList = WeatherStatusModel.getInstance().loadWeatherStatusList(DUMMY_CITY_NAME, false);
        forecastListView.displayWeatherList(weatherStatusList);
    }

    @Override
    public void onStop() {

    }

    public void onEventMainThread(DataEvent.NewWeatherStatusList event) {
        forecastListView.displayWeatherList(event.getWeatherStatusList());
    }

    public void onEventMainThread(DataEvent.LoadedWeatherStatusListErrorEvent event) {
        String errorMessage = event.getError();
        forecastListView.displayErrorMessage(errorMessage);
    }

    public void forceRefresh() {
        WeatherStatusModel.getInstance().loadWeatherStatusList(DUMMY_CITY_NAME, true);
    }
}
