package net.aung.sunshine.events;

import net.aung.sunshine.data.responses.WeatherStatusListResponse;
import net.aung.sunshine.data.vos.WeatherStatusVO;

import java.util.List;

/**
 * Created by aung on 12/14/15.
 */
public class DataEvent {

    public static class RefreshNewWeatherDataEvent {

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

        public LoadedWeatherStatusListEvent(WeatherStatusListResponse response) {
            this.response = response;
        }

        public WeatherStatusListResponse getResponse() {
            return response;
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
