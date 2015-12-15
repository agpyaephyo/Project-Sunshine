package net.aung.sunshine.mvp.views;

import net.aung.sunshine.data.vos.WeatherStatusVO;

/**
 * Created by aung on 12/15/15.
 */
public interface ForecastDetailView {
    void displayWeatherDetail(WeatherStatusVO weatherStatus);
}
