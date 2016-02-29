package net.aung.sunshine.views;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.databinding.ViewPodWeatherHpwGridBinding;
import net.aung.sunshine.databinding.ViewPodWeatherHpwLinearBinding;

/**
 * Created by aung on 2/29/16.
 */
public class ViewPodWeatherHPW {

    private ViewDataBinding binding;

    public ViewPodWeatherHPW(View view) {
        binding = DataBindingUtil.bind(view);
    }

    public void bind(WeatherStatusVO weatherStatus) {
        if (binding instanceof ViewPodWeatherHpwGridBinding) {
            ((ViewPodWeatherHpwGridBinding) binding).setWeatherStatus(weatherStatus);
        } else if (binding instanceof ViewPodWeatherHpwLinearBinding) {
            ((ViewPodWeatherHpwLinearBinding) binding).setWeatherStatus(weatherStatus);
        }
    }
}
