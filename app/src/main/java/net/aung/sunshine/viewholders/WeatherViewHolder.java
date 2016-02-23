package net.aung.sunshine.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.aung.sunshine.R;
import net.aung.sunshine.SunshineApplication;
import net.aung.sunshine.controllers.ForecastListScreenController;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.utils.SettingsUtils;
import net.aung.sunshine.utils.WeatherDataUtils;

/**
 * Created by aung on 12/13/15.
 */
public abstract class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected ForecastListScreenController controller;
    protected WeatherStatusVO weatherStatus;
    protected WeatherViewHolderController weatherVHController;

    protected View mItemView;

    public WeatherViewHolder(View itemView, ForecastListScreenController controller, WeatherViewHolderController weatherVHController) {
        super(itemView);
        this.controller = controller;
        this.weatherVHController = weatherVHController;
        this.mItemView = itemView;
    }

    public void bind(WeatherStatusVO status, int selectedRow) {
        this.weatherStatus = status;
        int position = getAdapterPosition();
        mItemView.setSelected(false);
        if(position != RecyclerView.NO_POSITION && position == selectedRow){
            mItemView.setSelected(true);
        }
    }

    @Override
    public void onClick(View view) {
        Context context = SunshineApplication.getContext();
        if (context.getResources().getBoolean(R.bool.isTwoPane)) {
            view.setSelected(true);
        }

        weatherVHController.onResetSelectedItem(getAdapterPosition());
        controller.onNavigateToForecastDetail(weatherStatus);
    }

    public void setSelection(boolean isSetSelection) {
        mItemView.setSelected(isSetSelection);
    }

    public interface WeatherViewHolderController {
        void onResetSelectedItem(int newlySelectedItemIndex);
    }
}
