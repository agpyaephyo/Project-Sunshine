package net.aung.sunshine.data.vos;

import com.google.gson.annotations.SerializedName;

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
        return String.valueOf((int)minTemperature) + (char) 0x00B0;
    }

    public String getMaxTemperatureDisplay() {
        return String.valueOf((int)maxTemperature) + (char) 0x00B0;
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
}
