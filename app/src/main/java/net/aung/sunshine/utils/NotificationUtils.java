package net.aung.sunshine.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import net.aung.sunshine.data.vos.WeatherStatusVO;

import java.util.concurrent.ExecutionException;

/**
 * Created by aung on 2/17/16.
 */
public class NotificationUtils {

    private static final int WEATHER_NOTIFICATION_ID = 3004;

    public static void showWeatherNotification(WeatherStatusVO weather) {
        Context context = SunshineApplication.getContext();

        //Notification Icon
        int weatherArtResourceId = WeatherDataUtils.getArtResourceForWeatherCondition(weather.getWeather().getId());

        try {
            Bitmap weatherArtBitmap = getBitmapForNotification(weather);

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

    private static Bitmap getBitmapForNotification(WeatherStatusVO weather) throws ExecutionException, InterruptedException {
        Context context = SunshineApplication.getContext();
        int weatherArtResourceId = WeatherDataUtils.getArtResourceForWeatherCondition(weather.getWeather().getId());
        Bitmap weatherArtBitmap;
        if (SettingsUtils.retrieveIconPackPref() == SettingsUtils.ICON_PACK_UDACITY) {
            int largeIconWidth = context.getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_width); //Don't need to check for HoneyComb version because minimum API version is 16.
            int largeIconHeight = context.getResources().getDimensionPixelSize(android.R.dimen.notification_large_icon_height);
            String artUrl = WeatherDataUtils.getArtUrlFromWeatherCondition(weather.getWeather().getId());

            weatherArtBitmap = Glide.with(context)
                    .load(artUrl)
                    .asBitmap()
                    .placeholder(weatherArtResourceId)
                    .error(weatherArtResourceId)
                    .into(largeIconWidth, largeIconHeight)
                    .get();
        } else {
            weatherArtBitmap = BitmapFactory.decodeResource(context.getResources(), weatherArtResourceId);
        }

        return weatherArtBitmap;
    }

    public static void hideWeatherNotification() {
        Context context = SunshineApplication.getContext();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(WEATHER_NOTIFICATION_ID);
    }
}
