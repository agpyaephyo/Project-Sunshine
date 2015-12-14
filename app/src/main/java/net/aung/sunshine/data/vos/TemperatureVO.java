package net.aung.sunshine.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 12/14/15.
 */
public class TemperatureVO {

    @SerializedName("day")
    private int dayTemperature;

    @SerializedName("night")
    private int nightTemperature;

    @SerializedName("min")
    private int minTemperature;

    @SerializedName("max")
    private int maxTemperature;

    @SerializedName("morn")
    private int morningTemperature;

    @SerializedName("eve")
    private int eveningTemperature;

    public int getDayTemperature() {
        return dayTemperature;
    }

    public int getNightTemperature() {
        return nightTemperature;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public int getMorningTemperature() {
        return morningTemperature;
    }

    public int getEveningTemperature() {
        return eveningTemperature;
    }
}
