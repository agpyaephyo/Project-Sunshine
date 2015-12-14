package net.aung.sunshine.viewholders;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.ImageView;

import net.aung.sunshine.R;
import net.aung.sunshine.data.vos.DailyWeatherStatusVO;
import net.aung.sunshine.databinding.ListItemForecastTodayBinding;
import net.aung.sunshine.utils.WeatherIconUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 12/13/15.
 */
public class TodayWeatherViewHolder extends WeatherViewHolder {

    @Bind(R.id.iv_status_art)
    ImageView ivStatusArt;

    private ListItemForecastTodayBinding binding;

    public TodayWeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    public void bind(DailyWeatherStatusVO status) {
        binding.setWeather(status);
        int weatherArtResourceId = WeatherIconUtils.getArtResourceForWeatherCondition(status.getWeatherId());
        ivStatusArt.setImageResource(weatherArtResourceId);
    }
}
