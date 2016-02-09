package net.aung.sunshine.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.aung.sunshine.controllers.ForecastListScreenController;
import net.aung.sunshine.data.vos.WeatherStatusVO;

/**
 * Created by aung on 12/13/15.
 */
public abstract class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected ForecastListScreenController controller;
    protected WeatherStatusVO weatherStatus;

    public WeatherViewHolder(View itemView, ForecastListScreenController controller) {
        super(itemView);
        this.controller = controller;
    }

    public void bind(WeatherStatusVO status) {
        this.weatherStatus = status;
    }

    @Override
    public void onClick(View view) {
        controller.onNavigateToForecastDetail(weatherStatus);
    }
}
