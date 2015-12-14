package net.aung.sunshine.mvp.views;

import net.aung.sunshine.data.vos.WeatherStatusVO;

import java.util.List;

/**
 * Created by aung on 12/14/15.
 */
public interface ForecastListView {
    void displayWeatherList(List<WeatherStatusVO> weatherStatusList);
}
