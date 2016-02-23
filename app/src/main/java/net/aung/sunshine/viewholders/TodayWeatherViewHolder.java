package net.aung.sunshine.viewholders;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import net.aung.sunshine.R;
import net.aung.sunshine.controllers.ForecastListScreenController;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.databinding.ListItemForecastTodayBinding;
import net.aung.sunshine.utils.SettingsUtils;
import net.aung.sunshine.utils.WeatherDataUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aung on 12/13/15.
 */
public class TodayWeatherViewHolder extends WeatherViewHolder
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Bind(R.id.iv_status_art)
    ImageView ivStatusArt;

    @Bind(R.id.rl_list_forecast_today_root)
    RelativeLayout rlListForecastTodayRoot;

    private WeatherStatusVO mStatus;
    private ListItemForecastTodayBinding binding;

    public TodayWeatherViewHolder(View itemView, ForecastListScreenController controller, WeatherViewHolderController weatherVHController) {
        super(itemView, controller, weatherVHController);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);

        binding = DataBindingUtil.bind(itemView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rlListForecastTodayRoot.setElevation(rlListForecastTodayRoot.getContext().getResources().getDimension(R.dimen.toolbar_elevation));
        }

        PreferenceManager.getDefaultSharedPreferences(itemView.getContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void bind(WeatherStatusVO status, int selectedRow) {
        super.bind(status, selectedRow);
        this.mStatus = status;
        binding.setWeatherStatus(status);

        setArtForWeather(status);
    }

    @Override
    protected void finalize() throws Throwable {
        if (itemView != null) {
            PreferenceManager.getDefaultSharedPreferences(itemView.getContext())
                    .unregisterOnSharedPreferenceChangeListener(this);
        }
        super.finalize();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Context context = itemView.getContext();
        if (key.equals(context.getString(R.string.pref_icon_key))) {
            setArtForWeather(mStatus);
        }
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
