package net.aung.sunshine;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import net.aung.sunshine.data.models.WeatherStatusModel;
import net.aung.sunshine.events.DataEvent;
import net.aung.sunshine.utils.SettingsUtils;

import de.greenrobot.event.EventBus;

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

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }

        loadWeatherFromNetwork();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        EventBus eventBus = EventBus.getDefault();
        eventBus.unregister(this);
    }

    public static Context getContext() {
        return context;
    }

    public void onEventMainThread(DataEvent.PreferenceCityChangeEvent event) {
        Log.d(SunshineApplication.TAG, "Retrieving weather data for new city : " + event.getNewCity());
        WeatherStatusModel.getInstance().loadWeatherStatusList(event.getNewCity(), true);
    }

    private void loadWeatherFromNetwork() {
        String userLocation = SettingsUtils.retrieveUserLocation();
        Log.d(SunshineApplication.TAG, "Retrieving weather data for city : " + userLocation);

        WeatherStatusModel.getInstance().loadWeatherStatusList(userLocation, true);
    }
}
