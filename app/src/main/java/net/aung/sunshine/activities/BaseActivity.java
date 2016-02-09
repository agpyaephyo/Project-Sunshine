package net.aung.sunshine.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by aung on 12/10/15.
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * Use this method to start Settings Activity for Sunshine App.
     */
    protected void startSettingActivity() {
        Intent intentToSettings = SettingsActivity.newIntent(getApplicationContext());
        startActivity(intentToSettings);
    }

    protected void showCityInGoogleMap(String city, View viewForErrorSnackBar) {
        //String mapUrl = "http://maps.google.co.in/maps?q=" + city;

        Uri mapUri = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", city)
                .build();

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  mapUri);
        //intent.setPackage("com.google.android.apps.maps"); //only open with Google Map App
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Snackbar.make(viewForErrorSnackBar, "You have no app being installed to show your selected city on the Map. Please install app like Google Map.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
