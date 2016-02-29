package net.aung.sunshine.views;

import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.aung.sunshine.R;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.databinding.ViewPodWeatherInfoBinding;
import net.aung.sunshine.utils.SettingsUtils;
import net.aung.sunshine.utils.WeatherDataUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 2/29/16.
 */
public class ViewPodWeatherInfo {

    @Bind(R.id.iv_status_art)
    ImageView ivStatusArt;

    private ViewPodWeatherInfoBinding binding;

    public ViewPodWeatherInfo(View view) {
        binding = DataBindingUtil.bind(view);
        ButterKnife.bind(this, view);
    }

    public void bind(WeatherStatusVO weatherStatus) {
        binding.setWeatherStatus(weatherStatus);
        setArtForWeather(weatherStatus);
    }

    private void setArtForWeather(WeatherStatusVO status) {
        if (SettingsUtils.retrieveIconPackPref() == SettingsUtils.ICON_PACK_UDACITY) {
            String artUrl = WeatherDataUtils.getArtUrlFromWeatherCondition(status.getWeather().getId());
            Glide.with(ivStatusArt.getContext())
                    .load(artUrl)
                    .error(WeatherDataUtils.getArtResourceForWeatherCondition(status.getWeather().getId()))
                    .into(ivStatusArt);
        } else {
            int weatherArtResourceId = WeatherDataUtils.getArtResourceForWeatherCondition(status.getWeather().getId());
            ivStatusArt.setImageResource(weatherArtResourceId);
        }
    }
}
