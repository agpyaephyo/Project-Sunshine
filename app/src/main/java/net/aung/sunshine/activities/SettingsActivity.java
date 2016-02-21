package net.aung.sunshine.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import net.aung.sunshine.R;
import net.aung.sunshine.events.DataEvent;
import net.aung.sunshine.utils.SettingsUtils;

import de.greenrobot.event.EventBus;

/**
 * Created by aung on 2/7/16.
 */
public class SettingsActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    public static Intent newIntent(Context context) {
        Intent intentToSettings = new Intent(context, SettingsActivity.class);
        return intentToSettings;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add 'general' preferences, defined in the XML file
        addPreferencesFromResource(R.xml.pref_general);

        // For all preferences, attach an OnPreferenceChangeListener so the UI summary can be
        // updated when the preference changes.

        Preference cityPref = findPreference(getString(R.string.pref_location_key));
        cityPref.setOnPreferenceChangeListener(this);
        onPreferenceChange(cityPref, PreferenceManager.getDefaultSharedPreferences(cityPref.getContext())
                .getString(cityPref.getKey(), ""));

        Preference unitPref = findPreference(getString(R.string.pref_unit_key));
        unitPref.setOnPreferenceChangeListener(this);
        onPreferenceChange(unitPref, PreferenceManager.getDefaultSharedPreferences(unitPref.getContext())
                .getString(unitPref.getKey(), ""));


        Preference notificationPref = findPreference(getString(R.string.pref_enable_notification_key));
        notificationPref.setOnPreferenceChangeListener(this);
        onPreferenceChange(notificationPref, PreferenceManager.getDefaultSharedPreferences(notificationPref.getContext())
                .getBoolean(notificationPref.getKey(), true));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            String stringValue = value.toString();
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            } else {
                preference.setSummary(listPreference.getEntries()[0]); //user hasn't pick any unit yet. default value.
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            String stringValue = value.toString();
            preference.setSummary(stringValue);
        }

        if (preference.getKey().equals(getString(R.string.pref_location_key))) {
            String stringValue = value.toString();
            String oldCity = SettingsUtils.retrieveUserCity();
            if (!oldCity.equalsIgnoreCase(stringValue)) {
                SettingsUtils.saveUserCity(stringValue);
                EventBus.getDefault().post(new DataEvent.PreferenceCityChangeEvent(stringValue));
            }
        } else if (preference.getKey().equals(getString(R.string.pref_enable_notification_key))) {
            boolean newPref = (boolean) value;
            boolean oldPref = SettingsUtils.retrieveNotificationPref();
            if(newPref != oldPref) {
                SettingsUtils.saveNotificationPref(newPref);
                EventBus.getDefault().post(new DataEvent.PreferenceNotificationChangeEvent(newPref));
            }
        }

        return true;
    }
}
