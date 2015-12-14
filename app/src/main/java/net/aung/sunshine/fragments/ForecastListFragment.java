package net.aung.sunshine.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.aung.sunshine.R;
import net.aung.sunshine.adapters.ForecastListAdapter;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.data.models.WeatherStatusModel;
import net.aung.sunshine.mvp.presenters.ForecastListPresenter;
import net.aung.sunshine.mvp.views.ForecastListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastListFragment extends BaseFragment implements ForecastListView {

    @Bind(R.id.rv_forecasts)
    RecyclerView rvForecasts;

    private ForecastListAdapter adapter;
    private ForecastListPresenter presenter;

    public static ForecastListFragment newInstance() {
        ForecastListFragment fragment = new ForecastListFragment();
        return fragment;
    }

    public ForecastListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ForecastListPresenter(this);
        presenter.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_forecast_list, container, false);
        ButterKnife.bind(this, rootView);

        rvForecasts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        adapter = ForecastListAdapter.newInstance();
        rvForecasts.setAdapter(adapter);

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
    public void displayWeatherList(List<WeatherStatusVO> weatherStatusList) {
        adapter.setStatusList(weatherStatusList);
    }
}
