package net.aung.sunshine.events;

import net.aung.sunshine.data.vos.WeatherStatusVO;

import java.util.List;

/**
 * Created by aung on 12/14/15.
 */
public class DataEvent {

    public static class Loaded14DaysWeatherEvent {
        private List<WeatherStatusVO> weatherStatusList;

        public Loaded14DaysWeatherEvent(List<WeatherStatusVO> weatherStatusList) {
            this.weatherStatusList = weatherStatusList;
        }

        public List<WeatherStatusVO> getWeatherStatusList() {
            return weatherStatusList;
        }
    }
}
