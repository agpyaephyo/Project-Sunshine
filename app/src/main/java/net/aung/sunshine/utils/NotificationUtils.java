package net.aung.sunshine.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import net.aung.sunshine.R;
import net.aung.sunshine.SunshineApplication;
import net.aung.sunshine.activities.ForecastActivity;
import net.aung.sunshine.data.vos.WeatherStatusVO;

/**
 * Created by aung on 2/17/16.
 */
public class NotificationUtils {

    private static final int WEATHER_NOTIFICATION_ID = 3004;

    public static void showWeatherNotification(WeatherStatusVO weather) {
        Context context = SunshineApplication.getContext();

        //Notification Icon
        int weatherArtResourceId = WeatherDataUtils.getArtResourceForWeatherCondition(weather.getWeather().getId());
        Bitmap weatherArtBitmap = BitmapFactory.decodeResource(context.getResources(), weatherArtResourceId);

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
    }

    public static void hideWeatherNotification() {
        Context context = SunshineApplication.getContext();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(WEATHER_NOTIFICATION_ID);
    }
}
