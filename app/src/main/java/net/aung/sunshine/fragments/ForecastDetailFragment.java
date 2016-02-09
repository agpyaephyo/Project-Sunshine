package net.aung.sunshine.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.aung.sunshine.R;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.databinding.FragmentForecastDetailBinding;
import net.aung.sunshine.mvp.presenters.ForecastDetailPresenter;
import net.aung.sunshine.mvp.views.ForecastDetailView;
import net.aung.sunshine.utils.WeatherIconUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForecastDetailFragment extends BaseFragment
        implements ForecastDetailView {

    private static final String ARG_DT = "ARG_DT";

    private long dateTime;
    private FragmentForecastDetailBinding binding;
    private ForecastDetailPresenter presenter;

    @Bind(R.id.iv_status_art)
    ImageView ivStatusArt;

    private View rootView;

    private ShareActionProvider mShareActionProvider;

    public ForecastDetailFragment() {

    }

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

        setHasOptionsMenu(true);
    }

    @Override
    protected void readArguments(Bundle bundle) {
        super.readArguments(bundle);
        dateTime = bundle.getLong(ARG_DT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_forecast_detail, container, false);
        ButterKnife.bind(this, rootView);
        binding = DataBindingUtil.bind(rootView);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_forecast_detail, menu);

        MenuItem shareMenuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareMenuItem);

        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareIntent());
        } else {
            Snackbar.make(rootView, "ShareActionProvider is being null. Why ?", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                getActivity().onBackPressed();
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
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setElevation(getResources().getDimension(R.dimen.toolbar_elevation));
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

    private Intent createShareIntent() {
        Intent myShareIntent = new Intent(Intent.ACTION_SEND);
        myShareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        myShareIntent.setType("text/plain");
        myShareIntent.putExtra(Intent.EXTRA_TEXT, "Hi, my name is Sunshine.");
        return myShareIntent;
    }
}
