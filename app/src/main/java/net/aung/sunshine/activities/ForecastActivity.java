package net.aung.sunshine.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.aung.sunshine.R;
import net.aung.sunshine.controllers.ForecastListScreenController;
import net.aung.sunshine.data.vos.WeatherStatusVO;
import net.aung.sunshine.fragments.ForecastDetailFragment;
import net.aung.sunshine.fragments.ForecastListFragment;

public class ForecastActivity extends BaseActivity
        implements ForecastListScreenController {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_list);

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, ForecastListFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forecast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                startSettingActivity();
                break;
            case R.id.action_about:
                Snackbar.make(fab, "About this Project Sunshine is coming soon", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;
            case R.id.action_help:
                Snackbar.make(fab, "The help that you gonna need to use this App is coming soon", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onNavigateToForecastDetail(WeatherStatusVO weatherStatus) {
        getSupportFragmentManager().beginTransaction()
                //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setCustomAnimations(R.anim.screen_enter_horizontal, R.anim.screen_exit_horizontal, R.anim.screen_pop_enter_horizontal, R.anim.screen_pop_exit_horizontal)
                .replace(R.id.fl_container, ForecastDetailFragment.newInstance(weatherStatus.getDateTime()))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showCityInGoogleMap(String city) {
        showCityInGoogleMap(city, fab);
    }
}
