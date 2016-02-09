package net.aung.sunshine.mvp.presenters;

import net.aung.sunshine.data.models.WeatherStatusModel;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.events.DataEvent;
import net.aung.sunshine.mvp.views.ForecastDetailView;
import net.aung.sunshine.utils.SettingsUtils;

/**
 * Created by aung on 12/15/15.
 */
public class ForecastDetailPresenter extends BasePresenter {

    private ForecastDetailView forecastDetailView;
    private long dateForWeatherDetail;

    public ForecastDetailPresenter(ForecastDetailView forecastDetailView, long dateForWeatherDetail) {
        this.forecastDetailView = forecastDetailView;
        this.dateForWeatherDetail = dateForWeatherDetail;
    }

    @Override
    public void onStart() {
        WeatherStatusVO weatherStatus = WeatherStatusModel.getInstance().loadWeatherStatusDetail(dateForWeatherDetail);

        if(weatherStatus != null)
            forecastDetailView.displayWeatherDetail(weatherStatus);
    }

    @Override
    public void onStop() {

    }

    public void onEventMainThread(DataEvent.NewWeatherStatusDetail event) {
        WeatherStatusVO weatherStatus = event.getWeatherStatus();
        forecastDetailView.displayWeatherDetail(weatherStatus);
    }
}
