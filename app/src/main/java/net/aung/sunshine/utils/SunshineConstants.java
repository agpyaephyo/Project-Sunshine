package net.aung.sunshine.utils;

import java.util.Date;

/**
 * Created by aung on 2/12/16.
 */
public class SunshineConstants {

    public static final long TODAY = new Date().getTime() / 1000 - (24 * 60 * 60); //to make sure we are showing for today also.

    public static final int FORECAST_LIST_LOADER = 0;
    public static final int FORECAST_DETAIL_LOADER = 1;
}
