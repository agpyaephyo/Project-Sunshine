package net.aung.sunshine.viewholders;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import net.aung.sunshine.R;
import net.aung.sunshine.controllers.WeatherListItemController;
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


    public DailyWeatherViewHolder(View itemView, WeatherListItemController controller) {
        super(itemView, controller);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);

        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    public void bind(WeatherStatusVO status) {
        super.bind(status);
        binding.setWeatherStatus(status);

        int weatherIconResourceId = WeatherIconUtils.getIconResourceForWeatherCondition(status.getWeather().getId());
        ivStatus.setImageResource(weatherIconResourceId);
    }
}
