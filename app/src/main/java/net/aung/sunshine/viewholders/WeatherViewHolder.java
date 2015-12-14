package net.aung.sunshine.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.aung.sunshine.data.vos.DailyWeatherStatusVO;

/**
 * Created by aung on 12/13/15.
 */
public abstract class WeatherViewHolder extends RecyclerView.ViewHolder {

    public WeatherViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(DailyWeatherStatusVO status);
}
