package net.aung.sunshine.data.vos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 12/14/15.
 */
public class CityVO {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("coord")
    private CoordinateVO coordinates;

    @SerializedName("country")
    private String country;

    @SerializedName("population")
    private long population;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CoordinateVO getCoordinates() {
        return coordinates;
    }

    public String getCountry() {
        return country;
    }

    public long getPopulation() {
        return population;
    }
}
