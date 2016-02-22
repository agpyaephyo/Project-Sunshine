package net.aung.sunshine.viewholders;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.ImageView;

import net.aung.sunshine.R;
import net.aung.sunshine.controllers.ForecastListScreenController;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.databinding.ListItemForecastBinding;
import net.aung.sunshine.utils.WeatherDataUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 12/10/15.
 */
public class DailyWeatherViewHolder extends WeatherViewHolder {

    @Bind(R.id.iv_status)
    ImageView ivStatus;

    private ListItemForecastBinding binding;


    public DailyWeatherViewHolder(View itemView, ForecastListScreenController controller, WeatherViewHolderController weatherVHController) {
        super(itemView, controller, weatherVHController);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);

        binding = DataBindingUtil.bind(itemView);
    }

    @Override
    public void bind(WeatherStatusVO status, int selectedRow) {
        super.bind(status, selectedRow);
        binding.setWeatherStatus(status);

        int weatherIconResourceId = WeatherDataUtils.getIconResourceForWeatherCondition(status.getWeather().getId());
        ivStatus.setImageResource(weatherIconResourceId);
    }
}
