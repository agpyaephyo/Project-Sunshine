package net.aung.sunshine.controllers;

import net.aung.sunshine.data.vos.WeatherStatusVO;

/**
 * Created by aung on 12/15/15.
 */
public interface ForecastListScreenController extends BaseController {

    void onNavigateToForecastDetail(WeatherStatusVO weatherStatus);

    void showCityInGoogleMap(String city);
}
