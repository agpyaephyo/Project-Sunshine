package net.aung.sunshine.network;

import net.aung.sunshine.BuildConfig;
import net.aung.sunshine.data.responses.WeatherStatusListResponse;
import net.aung.sunshine.events.DataEvent;
import net.aung.sunshine.utils.CommonInstances;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by aung on 12/14/15.
 */
public class WeatherDataSourceImpl implements WeatherDataSource {

    private static WeatherDataSource objInstance;
    private final OWMApi owmApi;

    private Callback apiCallback = new Callback() {

        @Override
        public void success(Object obj, Response response) {
            if (obj instanceof WeatherStatusListResponse) {
                WeatherStatusListResponse weatherStatusListResponse = (WeatherStatusListResponse) obj;

                DataEvent.LoadedWeatherStatusListEvent event = new DataEvent.LoadedWeatherStatusListEvent(weatherStatusListResponse);
                EventBus.getDefault().post(event);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            DataEvent.LoadedWeatherStatusListErrorEvent event = new DataEvent.LoadedWeatherStatusListErrorEvent(error);
            EventBus.getDefault().post(event);
        }
    };

    private WeatherDataSourceImpl() {
        RestAdapter openWeatherMapApi = new RestAdapter.Builder()
                .setEndpoint(NetworkConstants.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
                .setConverter(new GsonConverter(CommonInstances.getGsonInstance()))
                .build();

        owmApi = openWeatherMapApi.create(OWMApi.class);
    }

    public static WeatherDataSource getInstance() {
        if (objInstance == null) {
            objInstance = new WeatherDataSourceImpl();
        }

        return objInstance;
    }

    @Override
    public void getWeatherForecastList(String city) {
        owmApi.getDailyForecast(
                city,
                BuildConfig.OPEN_WEATHER_MAP_API_KEY,
                NetworkConstants.RESPONSE_FORMAT_JSON,
                NetworkConstants.RESPONSE_UNIT_METRIC,
                NetworkConstants.RESPONSE_COUNT_DEFAULT,
                apiCallback
        );
    }
}
