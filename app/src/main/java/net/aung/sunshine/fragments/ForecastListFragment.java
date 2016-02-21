package net.aung.sunshine.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.aung.sunshine.R;
import net.aung.sunshine.SunshineApplication;
import net.aung.sunshine.adapters.ForecastListAdapter;
import net.aung.sunshine.components.RecyclerViewWithEmptyView;
import net.aung.sunshine.controllers.ForecastListScreenController;
import net.aung.sunshine.data.persistence.WeatherContract;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.events.DataEvent;
import net.aung.sunshine.mvp.presenters.ForecastListPresenter;
import net.aung.sunshine.mvp.views.ForecastListView;
import net.aung.sunshine.utils.NetworkUtils;
import net.aung.sunshine.utils.SettingsUtils;
import net.aung.sunshine.utils.SunshineConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForecastListFragment extends BaseFragment
        implements ForecastListView,
        SwipeRefreshLayout.OnRefreshListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String ARG_SELECTED_ROW = "ARG_SELECTED_ROW";

    @Bind(R.id.rv_forecasts)
    RecyclerViewWithEmptyView rvForecasts;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    @Bind(R.id.vp_empty_forecasts)
    TextView tvEmptyForecasts;

    private View rootView;

    private ForecastListAdapter adapter;
    private ForecastListPresenter presenter;
    private ForecastListScreenController controller;

    private int mSelectedRow = RecyclerView.NO_POSITION;

    public static ForecastListFragment newInstance() {
        ForecastListFragment fragment = new ForecastListFragment();
        return fragment;
    }

    public ForecastListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        controller = (ForecastListScreenController) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ForecastListPresenter(this);
        presenter.onCreate();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_forecast_list, container, false);
        ButterKnife.bind(this, rootView);

        adapter = ForecastListAdapter.newInstance(controller, rvForecasts);

        rvForecasts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvForecasts.setAdapter(adapter);
        rvForecasts.setEmptyView(tvEmptyForecasts);

        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark);

        swipeContainer.setRefreshing(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(SunshineConstants.FORECAST_LIST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_forecast_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            /*
            case R.id.action_filter:
                Snackbar.make(rootView, "Later, you will be able to filter the list of dates that has specific weathers", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;
            */
            case R.id.action_show_city:
                String city = SettingsUtils.retrieveUserCity();
                controller.showCityInGoogleMap(city);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void refreshNewWeatherData() {
        //won't display when the data is coming back from db.
        if (swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
            Snackbar.make(rootView, "New weather data has been refreshed.", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void displayErrorMessage(String message) {
        if (swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
        }

        Snackbar.make(rootView, "Failed to load weather status list (" + message + ")", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        setActionBarElevation(true);
    }

    @Override
    public void onRefresh() {
        presenter.forceRefresh();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String city = SettingsUtils.retrieveUserCity();
        if (city != null) {
            Log.d(SunshineApplication.TAG, "Retrieving weather data for city (from db) : " + city);

            return new CursorLoader(getActivity(),
                    WeatherContract.WeatherEntry.buildWeatherUriWithStartDate(city, SunshineConstants.TODAY),
                    null, //projections
                    null, //selection
                    null, //selectionArgs
                    WeatherContract.WeatherEntry.TABLE_NAME + "." + WeatherContract.WeatherEntry.COLUMN_DATE + " ASC");
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursorWeather) {
        List<WeatherStatusVO> weatherStatusList = new ArrayList<>();
        if (cursorWeather.moveToFirst()) {
            do {
                weatherStatusList.add(WeatherStatusVO.parseFromCursor(cursorWeather));
            } while (cursorWeather.moveToNext());
        }

        adapter.setStatusList(weatherStatusList);
        setActionBarElevation(weatherStatusList.size() == 0);

        if(weatherStatusList.size() == 0 && !NetworkUtils.isOnline(getContext())) {
            tvEmptyForecasts.setText(getString(R.string.error_no_network));
        }

        if (mSelectedRow != RecyclerView.NO_POSITION) {
            if (getResources().getBoolean(R.bool.isTwoPane)) {
                adapter.setSelectedRow(mSelectedRow);
            }
            rvForecasts.smoothScrollToPosition(mSelectedRow);
        } else {
            if (getResources().getBoolean(R.bool.isTwoPane)) {
                adapter.setSelectedRow(0);
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.setStatusList(new ArrayList<WeatherStatusVO>());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (adapter != null) {
            outState.putInt(ARG_SELECTED_ROW, adapter.getSelectedRow());
        } else {
            if (mSelectedRow != RecyclerView.NO_POSITION) {
                outState.putInt(ARG_SELECTED_ROW, mSelectedRow);
            }
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        //all of the following could also be done by reading the bundle inside onCreateView.
        if (savedInstanceState != null) {
            mSelectedRow = savedInstanceState.getInt(ARG_SELECTED_ROW, RecyclerView.NO_POSITION);
        }
    }

    public void onEventMainThread(DataEvent.PreferenceCityChangeEvent event) {
        getLoaderManager().restartLoader(SunshineConstants.FORECAST_LIST_LOADER, null, this);
    }

    private void setActionBarElevation(boolean isElevationSet) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);
        if (isElevationSet) {
            actionBar.setElevation(getResources().getDimension(R.dimen.toolbar_elevation));
        } else {
            actionBar.setElevation(0f);
        }
    }
}
