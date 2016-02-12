package net.aung.sunshine;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import net.aung.sunshine.data.models.WeatherStatusModel;
import net.aung.sunshine.utils.SettingsUtils;

/**
 * Created by aung on 12/9/15.
 */
public class SunshineApplication extends Application {

    public static final String TAG = SunshineApplication.class.getSimpleName(); // all the logging should have this as Log Tag.

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        loadWeatherFromNetwork();
    }

    public static Context getContext() {
        return context;
    }

    private void loadWeatherFromNetwork() {
        String userLocation = SettingsUtils.retrieveUserLocation();
        Log.d(SunshineApplication.TAG, "Retrieving weather data for city : " + userLocation);

        WeatherStatusModel.getInstance().loadWeatherStatusList(userLocation, true);
    }
}
