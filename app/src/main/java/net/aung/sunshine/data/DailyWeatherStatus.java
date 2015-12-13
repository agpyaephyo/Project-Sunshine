package net.aung.sunshine.data;

import android.support.annotation.NonNull;

/**
 * This object is immutable.
 */
public class DailyWeatherStatus {

    private final int weatherId;
    private final String status;
    private final int maxTemperature;
    private final int minTemperature;
    private final String statusImageUrl;
    private final String day;

    public DailyWeatherStatus(int weatherId, @NonNull String date, @NonNull String status, int maxTemperature, int minTemperature) {
        this.weatherId = weatherId;
        this.status = status;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;

        //base on the status, the imageUrl will be set.
        this.statusImageUrl = null;

        //TODO parse that date into Date obj and retrieve the day from it.
        this.day = date;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public String getStatus() {
        return status;
    }

    public String getMaxTemperature() {
        return String.valueOf(maxTemperature) + (char) 0x00B0;
    }

    public String getMinTemperature() {
        return String.valueOf(minTemperature) + (char) 0x00B0;
    }

    public String getStatusImageUrl() {
        return statusImageUrl;
    }

    public String getDay() {
        return day;
    }
}
