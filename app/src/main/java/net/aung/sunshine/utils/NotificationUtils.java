package net.aung.sunshine.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.bumptech.glide.Glide;

import net.aung.sunshine.R;
import net.aung.sunshine.SunshineApplication;
import net.aung.sunshine.activities.ForecastActivity;
import net.aung.sunshine.data.persistence.WeatherContract;
import net.aung.sunshine.data.vos.WeatherStatusVO;

import java.util.concurrent.ExecutionException;

/**
 * Created by aung on 2/17/16.
 */
public class NotificationUtils {

    private static final int WEATHER_NOTIFICATION_ID = 3004;
    private static final int ALERT_NOTIFICATION_ID = 3005;

    public static void showWeatherNotification(WeatherStatusVO weather) {
        Context context = SunshineApplication.getContext();

        //Notification Icon
        int weatherArtResourceId = WeatherDataUtils.getArtResourceForWeatherCondition(weather.getWeather().getId());

        try {
            Bitmap weatherArtBitmap = ImageUtils.getBitmapForNotification(weather);

            //Notification Title
            String title = context.getString(R.string.app_name);

            //Notification Text
            String text = String.format(context.getString(R.string.format_notification),
                    weather.getWeather().getDescription(),
                    weather.getTemperature().getMaxTemperatureDisplay(),
                    weather.getTemperature().getMinTemperatureDisplay());

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setColor(context.getResources().getColor(R.color.primary))
                    .setSmallIcon(weatherArtResourceId)
                    .setLargeIcon(weatherArtBitmap)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(text));

            //Open the app when user tap on notification
            Intent resultIntent = new Intent(context, ForecastActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(WEATHER_NOTIFICATION_ID, builder.build());
        } catch (InterruptedException e) {
            Log.e(SunshineApplication.TAG, e.getMessage());
        } catch (ExecutionException e) {
            Log.e(SunshineApplication.TAG, e.getMessage());
        }
    }

    public static void showAlertNotification(String message) {
        Context context = SunshineApplication.getContext();

        Bitmap weatherArtBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.art_storm);

        //Notification Title
        String title = context.getString(R.string.app_name);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setColor(context.getResources().getColor(R.color.primary))
                .setSmallIcon(R.drawable.art_clear)
                .setLargeIcon(weatherArtBitmap)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));

        //Open the app when user tap on notification
        Intent resultIntent = new Intent(context, ForecastActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ALERT_NOTIFICATION_ID, builder.build());
    }

    public static void hideWeatherNotification() {
        Context context = SunshineApplication.getContext();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(WEATHER_NOTIFICATION_ID);
    }

    public static void showUpdatedWeatherNotification() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Context context = SunshineApplication.getContext();
                String city = SettingsUtils.retrieveUserCity();
                Cursor cursorWeather = context.getContentResolver().query(WeatherContract.WeatherEntry.buildWeatherUriWithStartDate(city, SunshineConstants.TODAY),
                        null, null, null, null);

                if (cursorWeather.moveToFirst()) {
                    WeatherStatusVO weatherStatusDetail = WeatherStatusVO.parseFromCursor(cursorWeather);
                    NotificationUtils.showWeatherNotification(weatherStatusDetail);
                }
            }
        }).start();
    }
}
