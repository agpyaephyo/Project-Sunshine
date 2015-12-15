package net.aung.sunshine.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.aung.sunshine.R;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.databinding.FragmentForecastDetailBinding;
import net.aung.sunshine.mvp.presenters.ForecastDetailPresenter;
import net.aung.sunshine.mvp.views.ForecastDetailView;
import net.aung.sunshine.utils.WeatherIconUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Use the {@link ForecastDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastDetailFragment extends BaseFragment
        implements ForecastDetailView{

    private static final String ARG_DT = "ARG_DT";

    private long dateTime;
    private FragmentForecastDetailBinding binding;
    private ForecastDetailPresenter presenter;

    @Bind(R.id.iv_status_art)
    ImageView ivStatusArt;

    public ForecastDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param dateTime unique dateTime value for daily weather data.
     * @return A new instance of fragment ForecastDetailFragment.
     */
    public static ForecastDetailFragment newInstance(long dateTime) {
        ForecastDetailFragment fragment = new ForecastDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_DT, dateTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ForecastDetailPresenter(this, dateTime);
        presenter.onCreate();
    }

    @Override
    protected void readArguments(Bundle bundle) {
        super.readArguments(bundle);
        dateTime = bundle.getLong(ARG_DT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_forecast_detail, container, false);
        ButterKnife.bind(this, rootView);
        binding = DataBindingUtil.bind(rootView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void displayWeatherDetail(WeatherStatusVO weatherStatus) {
        binding.setWeatherStatus(weatherStatus);

        int weatherArtResourceId = WeatherIconUtils.getArtResourceForWeatherCondition(weatherStatus.getWeather().getId());
        ivStatusArt.setImageResource(weatherArtResourceId);
    }
}
