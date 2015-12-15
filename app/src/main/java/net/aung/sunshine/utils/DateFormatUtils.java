package net.aung.sunshine.utils;

import java.text.SimpleDateFormat;

/**
 * Created by aung on 12/14/15.
 */
public class DateFormatUtils {

    public static final SimpleDateFormat sdfWeatherStatusDate = new SimpleDateFormat("EE MMM dd");
    public static final SimpleDateFormat sdfWeatherStatusDateToday = new SimpleDateFormat("MMMM dd");
    public static final SimpleDateFormat sdfWeatherStatusDateTomorrow = new SimpleDateFormat("MMM dd");
    public static final SimpleDateFormat sdfDay = new SimpleDateFormat("EEEE");

}
