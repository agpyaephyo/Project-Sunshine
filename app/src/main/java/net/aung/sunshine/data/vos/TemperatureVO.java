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

    public String getDayTemperature() {
        return String.valueOf((int)dayTemperature) + (char) 0x00B0;
    }

    public String getNightTemperature() {
        return String.valueOf((int)nightTemperature) + (char) 0x00B0;
    }

    public String getMinTemperature() {
        return String.valueOf((int)minTemperature) + (char) 0x00B0;
    }

    public String getMaxTemperature() {
        return String.valueOf((int)maxTemperature) + (char) 0x00B0;
    }

    public String getMorningTemperature() {
        return String.valueOf((int)morningTemperature) + (char) 0x00B0;
    }

    public String getEveningTemperature() {
        return String.valueOf((int)eveningTemperature) + (char) 0x00B0;
    }
}
