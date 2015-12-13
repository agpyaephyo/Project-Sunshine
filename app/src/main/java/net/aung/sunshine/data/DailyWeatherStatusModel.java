package net.aung.sunshine.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 12/10/15.
 */
public class DailyWeatherStatusModel {

    public static List<DailyWeatherStatus> initDummyWeatherStatusList() {
        List<DailyWeatherStatus> dummyStatusList = new ArrayList<>();
        dummyStatusList.add(new DailyWeatherStatus(803, "Today", "Clouds", 21, 13));
        dummyStatusList.add(new DailyWeatherStatus(800, "Tomorrow", "Clear", 18, 11));
        dummyStatusList.add(new DailyWeatherStatus(800, "Monday", "Clear", 16, 11));
        dummyStatusList.add(new DailyWeatherStatus(804, "Tuesday", "Clouds", 14, 14));

        dummyStatusList.add(new DailyWeatherStatus(220, "Wednesday", "Storm", 14, 13));
        dummyStatusList.add(new DailyWeatherStatus(502, "27th Jun 16", "Rains", 14, 13));
        dummyStatusList.add(new DailyWeatherStatus(610, "28th Jun 16", "Snow", 18, 13));
        dummyStatusList.add(new DailyWeatherStatus(750, "29th Jun 16", "Fog", 13, 10));
        return dummyStatusList;
    }
}
