package net.aung.sunshine.viewholders;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.ImageView;

import net.aung.sunshine.R;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.databinding.ListItemForecastBinding;
import net.aung.sunshine.utils.WeatherIconUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 12/10/15.
 */
public class DailyWeatherViewHolder extends WeatherViewHolder {

    @Bind(R.id.iv_status)
    ImageView ivStatus;

    private ListItemForecastBinding binding;

    public DailyWeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    public void bind(WeatherStatusVO status) {
        binding.setWeatherStatus(status);
        int weatherIconResourceId = WeatherIconUtils.getIconResourceForWeatherCondition(status.getWeather().getId());
        ivStatus.setImageResource(weatherIconResourceId);
    }
}
