package net.aung.sunshine.data.vos;

import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import net.aung.sunshine.R;
import net.aung.sunshine.SunshineApplication;
import net.aung.sunshine.data.persistence.WeatherContract;
import net.aung.sunshine.utils.SettingsUtils;

/**
 * Created by aung on 12/14/15.
 */
public class TemperatureVO {

    @SerializedName("day")
    private double dayTemperature;

    @SerializedName("night")
    private double nightTemperature;

    @SerializedName("min")
    private double minTemperature;

    @SerializedName("max")
    private double maxTemperature;

    @SerializedName("morn")
    private double morningTemperature;

    @SerializedName("eve")
    private double eveningTemperature;

    public String getDayTemperatureDisplay() {
        return String.valueOf((int)dayTemperature) + (char) 0x00B0;
    }

    public String getNightTemperatureDisplay() {
        return String.valueOf((int)nightTemperature) + (char) 0x00B0;
    }

    public String getMinTemperatureDisplay() {
        double temperature = getTemperatureBySelectedUnit(minTemperature);
        return String.valueOf((int)temperature) + (char) 0x00B0;
    }

    public String getMaxTemperatureDisplay() {
        double temperature = getTemperatureBySelectedUnit(maxTemperature);
        return String.valueOf((int)temperature) + (char) 0x00B0;
    }

    public String getMorningTemperatureDisplay() {
        return String.valueOf((int)morningTemperature) + (char) 0x00B0;
    }

    public String getEveningTemperatureDisplay() {
        return String.valueOf((int)eveningTemperature) + (char) 0x00B0;
    }

    public double getDayTemperature() {
        return dayTemperature;
    }

    public double getNightTemperature() {
        return nightTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getMorningTemperature() {
        return morningTemperature;
    }

    public double getEveningTemperature() {
        return eveningTemperature;
    }

    private double getTemperatureBySelectedUnit(double temperature) {
        String selectedUnit = SettingsUtils.retrieveSelectedUnit();
        Context context = SunshineApplication.getContext();
        if(selectedUnit.equalsIgnoreCase(context.getString(R.string.pref_unit_imperial))) {
            temperature = (temperature * 1.8) + 32;
        }

        return temperature;
    }

    public static TemperatureVO parseFromCursor(Cursor cursor) {
        TemperatureVO temperature = new TemperatureVO();
        temperature.minTemperature = cursor.getDouble(cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MIN_TEMPERATURE));
        temperature.maxTemperature = cursor.getDouble(cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_MAX_TEMPERATURE));
        return temperature;
    }
}
