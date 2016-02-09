package net.aung.sunshine.data.persistence;

import android.provider.BaseColumns;

/**
 * Created by aung on 2/8/16.
 */
public class WeatherContract {

    public static final class WeatherEntry implements BaseColumns {

        public static final String TABLE_NAME = "weather";

        public static final String COLUMN_MIN_TEMPERATURE = "min_temperature";
        public static final String COLUMN_MAX_TEMPERATURE = "max_temperature";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_PRESSURE = "pressure";
        public static final String COLUMN_WIND_SPEED = "wind_speed";
        public static final String COLUMN_HUMIDITY = "humidity";
        public static final String COLUMN_WEATHER_CONDITION_ID = "weather_condition_id";
        public static final String COLUMN_WEATHER_DESC = "weather_desc";
        public static final String COLUMN_LOCATION_ID = "location_id";
    }

    public static final class LocationEntry implements BaseColumns {
        public static final String TABLE_NAME = "location";

        public static final String COLUMN_LOCATION_SETTING = "location_setting";
        public static final String COLUMN_CITY_NAME = "city_name";
        public static final String COLUMN_COORD_LAT = "coord_lat";
        public static final String COLUMN_COORD_LNG = "coord_lng";
    }
}
