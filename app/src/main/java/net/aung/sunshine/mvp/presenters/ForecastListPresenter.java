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

    private ForecastListView forecastListView;

    public ForecastListPresenter(ForecastListView forecastListView) {
        this.forecastListView = forecastListView;
    }

    @Override
    public void onStart() {
        List<WeatherStatusVO> weatherStatusList = WeatherStatusModel.getInstance().load14daysWeather(1880252); //Singapore City ID
        forecastListView.displayWeatherList(weatherStatusList);
    }

    @Override
    public void onStop() {

    }

    public void onEventMainThread(DataEvent.Loaded14DaysWeatherEvent event) {
        forecastListView.displayWeatherList(event.getWeatherStatusList());
    }
}