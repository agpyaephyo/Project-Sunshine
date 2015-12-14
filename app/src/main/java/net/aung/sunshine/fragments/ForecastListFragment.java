package net.aung.sunshine.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.aung.sunshine.R;
import net.aung.sunshine.adapters.ForecastListAdapter;
import net.aung.sunshine.data.vos.DailyWeatherStatusVO;
import net.aung.sunshine.data.models.DailyWeatherStatusModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastListFragment extends BaseFragment {

    @Bind(R.id.rv_forecasts)
    RecyclerView rvForecasts;

    private ForecastListAdapter adapter;

    public static ForecastListFragment newInstance() {
        ForecastListFragment fragment = new ForecastListFragment();
        return fragment;
    }

    public ForecastListFragment() {
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
        List<DailyWeatherStatusVO> dummyStatusList = DailyWeatherStatusModel.getInstance().load14daysWeather(1880252); //Singapore City ID
        adapter.setStatusList(dummyStatusList);
    }
}
