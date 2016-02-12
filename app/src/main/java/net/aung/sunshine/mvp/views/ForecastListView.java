package net.aung.sunshine.mvp.views;

/**
 * Created by aung on 12/14/15.
 */
public interface ForecastListView {
    void refreshNewWeatherData();

    void displayErrorMessage(String message);
}
