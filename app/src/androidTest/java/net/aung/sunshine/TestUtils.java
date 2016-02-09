package net.aung.sunshine;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import net.aung.sunshine.data.persistence.WeatherContract;

import java.util.Map;
import java.util.Set;

/**
 * Created by aung on 2/9/16.
 */
public class TestUtils extends AndroidTestCase {

    public static final String TEST_LOCATION = "99705";
    public static final long TEST_DATE = 1419033600L; // December 20th, 2014

    public static ContentValues createNorthPoleLocationValues() {
        ContentValues northPoleLocationValues = new ContentValues();
        northPoleLocationValues.put(WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING, TEST_LOCATION);
        northPoleLocationValues.put(WeatherContract.LocationEntry.COLUMN_CITY_NAME, "North Pole");
        northPoleLocationValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LAT, 64.7488);
        northPoleLocationValues.put(WeatherContract.LocationEntry.COLUMN_COORD_LNG, -147.353);

        return northPoleLocationValues;
    }

    public static ContentValues createTestWeatherValues(long locationId) {
        ContentValues testWeatherValues = new ContentValues();
        testWeatherValues.put(WeatherContract.WeatherEntry.COLUMN_LOCATION_ID, locationId);
        testWeatherValues.put(WeatherContract.WeatherEntry.COLUMN_DATE, TEST_DATE);

        testWeatherValues.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, 1.2);
        testWeatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, 1.3);
        testWeatherValues.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMPERATURE, 75);
        testWeatherValues.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMPERATURE, 65);
        testWeatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_DESC, "Asteroids");
        testWeatherValues.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, 5.5);
        testWeatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_CONDITION_ID, 321);

        return testWeatherValues;
    }

    public static void validateCurrentCursorRow(String errorMsg, Cursor cursor, ContentValues values) {
        Set<Map.Entry<String, Object>> valueSet = values.valueSet();
        for (Map.Entry<String, Object> eachEntry : valueSet) {
            String columnName = eachEntry.getKey();
            int columnIndex = cursor.getColumnIndex(columnName);
            assertTrue(errorMsg + " The column, "+columnName+" is not being retrieved.", columnIndex != -1);

            String value = eachEntry.getValue().toString();
            String cursorValue = cursor.getString(columnIndex);
            assertEquals(errorMsg + " The value for column, "+columnName+" is expected to be "+value+". And cursor has "+cursorValue+".", value, cursorValue);
        }
    }
}
