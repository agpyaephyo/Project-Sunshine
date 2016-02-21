package net.aung.sunshine;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import net.aung.sunshine.data.models.WeatherStatusModel;
import net.aung.sunshine.data.persistence.WeatherContract;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.events.DataEvent;
import net.aung.sunshine.sync.SunshineSyncAdapter;
import net.aung.sunshine.utils.NotificationUtils;
import net.aung.sunshine.utils.SettingsUtils;
import net.aung.sunshine.utils.SunshineConstants;

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

        loadWeatherDataFromNetwork();

        SunshineSyncAdapter.initializeSyncAdapter(getApplicationContext());
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

    public void onEventMainThread(DataEvent.PreferenceNotificationChangeEvent event) {
        if (event.getNewPref()) { //new pref is enable notification

            String city = SettingsUtils.retrieveUserCity();
            Cursor cursorWeather = context.getContentResolver().query(WeatherContract.WeatherEntry.buildWeatherUriWithStartDate(city, SunshineConstants.TODAY),
                    null, null, null, null);

            if (cursorWeather.moveToFirst()) {
                WeatherStatusVO weatherStatusDetail = WeatherStatusVO.parseFromCursor(cursorWeather);
                NotificationUtils.showWeatherNotification(weatherStatusDetail);
            }

        } else { //new pref is enable notification
            NotificationUtils.hideWeatherNotification();
        }
    }

    private void loadWeatherDataFromNetwork() {
        String userLocation = SettingsUtils.retrieveUserCity();
        if (userLocation != null) {
            Log.d(SunshineApplication.TAG, "Retrieving weather data for city : " + userLocation);
            WeatherStatusModel.getInstance().loadWeatherStatusList(userLocation, true);
        } else {
            EventBus.getDefault().post(new DataEvent.LoadedWeatherStatusListErrorEvent(getString(R.string.error_no_city_has_put)));
        }
    }
}
