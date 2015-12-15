package net.aung.sunshine.events;

import net.aung.sunshine.data.responses.WeatherStatusListResponse;
import net.aung.sunshine.data.vos.WeatherStatusVO;

import java.util.List;

/**
 * Created by aung on 12/14/15.
 */
public class DataEvent {

    public static class NewWeatherStatusList {
        private List<WeatherStatusVO> weatherStatusList;

        public NewWeatherStatusList(List<WeatherStatusVO> weatherStatusList) {
            this.weatherStatusList = weatherStatusList;
        }

        public List<WeatherStatusVO> getWeatherStatusList() {
            return weatherStatusList;
        }
    }

    public static class NewWeatherStatusDetail {
        private WeatherStatusVO weatherStatus;

        public NewWeatherStatusDetail(WeatherStatusVO weatherStatus) {
            this.weatherStatus = weatherStatus;
        }

        public WeatherStatusVO getWeatherStatus() {
            return weatherStatus;
        }
    }

    public static class LoadedWeatherStatusListEvent {
        private WeatherStatusListResponse response;
        private int loadingType;

        public LoadedWeatherStatusListEvent(WeatherStatusListResponse response, int loadingType) {
            this.response = response;
            this.loadingType = loadingType;
        }

        public WeatherStatusListResponse getResponse() {
            return response;
        }

        public int getLoadingType() {
            return loadingType;
        }
    }

    public static class LoadedWeatherStatusListErrorEvent {
        private String error;

        public LoadedWeatherStatusListErrorEvent(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}
