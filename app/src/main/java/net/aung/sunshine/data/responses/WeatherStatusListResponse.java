package net.aung.sunshine.data.responses;

import com.google.gson.annotations.SerializedName;

import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.data.vos.CityVO;

import java.util.ArrayList;

/**
 * Created by aung on 12/14/15.
 */
public class WeatherStatusListResponse {

    @SerializedName("city")
    private CityVO city;

    @SerializedName("cod")
    private int cod;

    @SerializedName("message")
    private double message;

    @SerializedName("cnt")
    private int count;

    @SerializedName("list")
    private ArrayList<WeatherStatusVO> weatherStatusList;

    public CityVO getCity() {
        return city;
    }

    public int getCod() {
        return cod;
    }

    public double getMessage() {
        return message;
    }

    public int getCount() {
        return count;
    }

    public ArrayList<WeatherStatusVO> getWeatherStatusList() {
        return weatherStatusList;
    }
}
