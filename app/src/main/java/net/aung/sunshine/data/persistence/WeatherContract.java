package net.aung.sunshine.data.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import net.aung.sunshine.SunshineApplication;

/**
 * Created by aung on 2/8/16.
 */
public class WeatherContract {

    public static final String CONTENT_AUTHORITY = SunshineApplication.class.getPackage().getName();
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_LOCATION = "location";
    public static final String PATH_WEATHER = "weather";

    public static final class WeatherEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEATHER).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_WEATHER;

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

        public static Uri buildWeatherUri(long id) {
            //content://net.aung.sunshine/weather/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildWeatherUri(String locationSetting) {
            //content://net.aung.sunshine/weather/London
            return CONTENT_URI.buildUpon()
                    .appendPath(locationSetting)
                    .build();
        }

        public static Uri buildWeatherUri(String locationSetting, long date) {
            //content://net.aung.sunshine/weather/London/12345678
            return CONTENT_URI.buildUpon()
                    .appendPath(locationSetting)
                    .appendPath(Long.toString(date))
                    .build();
        }

        public static Uri buildWeatherUriWithDate(long date) {
            //content://net.aung.sunshine/weather?date=12345678
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_DATE, Long.toString(date))
                    .build();
        }

        public static Uri buildWeatherUriWithStartDate(String locationSetting, long startDate) {
            //content://net.aung.sunshine/weather/London?date=12345678
            return CONTENT_URI.buildUpon()
                    .appendPath(locationSetting)
                    .appendQueryParameter(COLUMN_DATE, Long.toString(startDate))
                    .build();
        }

        public static String getLocationSettingFromUri(Uri uri) {
            //content://net.aung.sunshine/weather(0)/London(1)/12345678(2)
            return uri.getPathSegments().get(1);
        }

        public static long getDateFromUri(Uri uri) {
            //content://net.aung.sunshine/weather(0)/London(1)/12345678(2)
            return Long.parseLong(uri.getPathSegments().get(2));
        }

        public static long getDateParamFromUri(Uri uri) {
            //content://net.aung.sunshine/weather/London?date=12345678
            //content://net.aung.sunshine/weather/date=12345678
            String dateString = uri.getQueryParameter(COLUMN_DATE);
            if (dateString != null && dateString.length() > 0) {
                return Long.parseLong(dateString);
            } else {
                return 0;
            }
        }
    }

    public static final class LocationEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;

        public static final String TABLE_NAME = "location";

        public static final String COLUMN_LOCATION_SETTING = "location_setting";
        public static final String COLUMN_CITY_NAME = "city_name";
        public static final String COLUMN_COORD_LAT = "coord_lat";
        public static final String COLUMN_COORD_LNG = "coord_lng";

        public static Uri buildLocationUri(long id) {
            //content://net.aung.sunshine/location/1
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
