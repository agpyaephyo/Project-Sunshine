package net.aung.sunshine.events;

import net.aung.sunshine.data.vos.DailyWeatherStatusVO;

import java.util.List;

/**
 * Created by aung on 12/14/15.
 */
public class DataEvent {

    public static class Loaded14DaysWeatherEvent {
        private List<DailyWeatherStatusVO> weatherStatusList;

        public Loaded14DaysWeatherEvent(List<DailyWeatherStatusVO> weatherStatusList) {
            this.weatherStatusList = weatherStatusList;
        }

        public List<DailyWeatherStatusVO> getWeatherStatusList() {
            return weatherStatusList;
        }
    }
}
