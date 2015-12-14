package net.aung.sunshine.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * This object is immutable.
 */
public class DailyWeatherStatusVO {

    @SerializedName("dt")
    private long dateTime;

    @SerializedName("temp")
    private TemperatureVO temperature;

    @SerializedName("pressure")
    private double pressure;

    @SerializedName("humidity")
    private double humidity;

    @SerializedName("weather")
    private WeatherVO weather;

    @SerializedName("speed")
    private double windSpeed;

    @SerializedName("deg")
    private int deg;

    @SerializedName("clouds")
    private int clouds;

    @SerializedName("rain")
    private double rain;

    public long getDateTime() {
        return dateTime;
    }

    public TemperatureVO getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public WeatherVO getWeather() {
        return weather;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getDeg() {
        return deg;
    }

    public int getClouds() {
        return clouds;
    }

    public double getRain() {
        return rain;
    }
}
