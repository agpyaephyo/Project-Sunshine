package net.aung.sunshine;

import android.app.Application;
import android.content.Context;

/**
 * Created by aung on 12/9/15.
 */
public class SunshineApplication extends Application {

    public static final String TAG = SunshineApplication.class.getSimpleName(); // all the logging should have this as Log Tag.

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
