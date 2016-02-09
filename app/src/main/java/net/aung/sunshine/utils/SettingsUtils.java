package net.aung.sunshine.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import net.aung.sunshine.R;
import net.aung.sunshine.SunshineApplication;

/**
 * Created by aung on 2/7/16.
 */
public class SettingsUtils {

    private static final String DUMMY_CITY_NAME = "Singapore"; //ABCDEFG
    /**
     * Retrieve the city name that user set in Settings
     * @return city name
     */
    public static String retrieveUserLocation() {
        Context context = SunshineApplication.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String defaultLocation = context.getString(R.string.pref_location_default);
        String userLocation = defaultSharedPref.getString(context.getString(R.string.pref_location_key), defaultLocation);

        if (userLocation.equalsIgnoreCase(defaultLocation)) {
            userLocation = DUMMY_CITY_NAME;
        }

        return userLocation;
    }

    /**
     *
     * @return
     */
    public static String retrieveSelectedUnit() {
        Context context = SunshineApplication.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPref.getString(context.getString(R.string.pref_unit_key), context.getString(R.string.pref_unit_metric)); //return "metrics" if user hasn't pick any unit yet.
    }
}
