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

    /**
     * Retrieve the city name that user set in Settings
     * @return city name
     */
    public static String retrieveUserCity() {
        Context context = SunshineApplication.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String userLocation = defaultSharedPref.getString(context.getString(R.string.pref_location_key), "Rangoon"); //TODO remove Rangoon & set null.

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

    public static void saveUserLocation(String newCity) {
        Context context = SunshineApplication.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPref.edit().putString(context.getString(R.string.pref_location_key), newCity).commit();
    }

    public static boolean retrieveNotificationPref() {
        Context context = SunshineApplication.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPref.getBoolean(context.getString(R.string.pref_enable_notification_key), true);
    }

    public static void saveNotificationPref(boolean newPref) {
        Context context = SunshineApplication.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPref.edit().putBoolean(context.getString(R.string.pref_enable_notification_key), newPref).commit();
    }
}
