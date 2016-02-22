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
        String userLocation = defaultSharedPref.getString(context.getString(R.string.pref_location_key), "your current city"); //TODO remove Rangoon & set null.

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

    public static void saveUserCity(String newCity) {
        Context context = SunshineApplication.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPref.edit().putString(context.getString(R.string.pref_location_key), newCity).apply();
    }

    public static boolean retrieveNotificationPref() {
        Context context = SunshineApplication.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPref.getBoolean(context.getString(R.string.pref_enable_notification_key), true);
    }

    public static void saveNotificationPref(boolean newPref) {
        Context context = SunshineApplication.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPref.edit().putBoolean(context.getString(R.string.pref_enable_notification_key), newPref).apply();
    }

    public static void saveServerResponseStatus(@SunshineConstants.ServerStatus int status) {
        Context context = SunshineApplication.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPref.edit().putInt(context.getString(R.string.pref_server_response_status_key), status).apply();
    }

    public static int getServerResponseStatus() {
        Context context = SunshineApplication.getContext();
        SharedPreferences defaultSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPref.getInt(context.getString(R.string.pref_server_response_status_key), SunshineConstants.STATUS_SERVER_UNKNOWN);
    }
}
