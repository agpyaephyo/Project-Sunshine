package net.aung.sunshine.data.vos;

import com.google.gson.annotations.SerializedName;

import net.aung.sunshine.R;
import net.aung.sunshine.SunshineApplication;
import net.aung.sunshine.utils.DateFormatUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * This object is immutable.
 */
public class WeatherStatusVO {

    @SerializedName("dt")
    private long dateTime;

    @SerializedName("temp")
    private TemperatureVO temperature;

    @SerializedName("pressure")
    private double pressure;

    @SerializedName("humidity")
    private double humidity;

    @SerializedName("weather")
    private ArrayList<WeatherVO> weatherList;

    @SerializedName("speed")
    private double windSpeed;

    @SerializedName("deg")
    private int deg;

    @SerializedName("clouds")
    private int clouds;

    @SerializedName("rain")
    private double rain;

    private Date date;

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

    public ArrayList<WeatherVO> getWeatherList() {
        return weatherList;
    }

    public WeatherVO getWeather() {
        return weatherList.get(0);
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

    public String getDate() {
        if (date == null) {
            date = new Date(dateTime * 1000);
        }

        String dateText = DateFormatUtils.sdfWeatherStatusDate.format(date);
        Calendar calendar = Calendar.getInstance();
        int todayDate = calendar.get(Calendar.DATE);
        calendar.setTime(date);
        int weatherDate = calendar.get(Calendar.DATE);
        if (todayDate == weatherDate) {
            //today
            dateText = SunshineApplication.getContext().getString(R.string.lbl_today);
        } else if (todayDate +1 == weatherDate) {
            //tomorrow
            dateText = SunshineApplication.getContext().getString(R.string.lbl_tomorrow);
        }

        return dateText;
    }
}
