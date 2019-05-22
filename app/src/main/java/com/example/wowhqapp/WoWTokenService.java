package com.example.wowhqapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.presenters.WoWTokenServicePresenter;
import com.example.wowhqapp.repositories.SettingRepository;


public class WoWTokenService extends Service implements MainContract.WoWTokenServiceView {

    public static final String ACTION = "TOKEN_SERVICE";
    public static final String CHANNEL = "DEF";
    public static final Integer NOTIFICATION_ID = 1;
    private static final long[] VIBRATION_PATTERN = {0, 100, 50, 100};


    public static final String IS_FROM_ACTIVITY = BuildConfig.APPLICATION_ID + "_IS_FROM_ACTIVITY";

    private MainContract.WoWTokenServicePresenter mWoWTokenServicePresenter;
    private NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(WowhqApplication.LOG_TAG, "onCreate - Сервис");
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mWoWTokenServicePresenter = new WoWTokenServicePresenter(new SettingRepository(getSharedPreferences(SettingRepository.APP_PREFERENCES, Context.MODE_PRIVATE)),this);
        initChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(WowhqApplication.LOG_TAG, "onStartCommand - Сервис");
        mWoWTokenServicePresenter.init((intent !=null) && intent.getExtras().getBoolean(IS_FROM_ACTIVITY, false));

        return Service.START_REDELIVER_INTENT; //В случае прерывания должен перезапуститься
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWoWTokenServicePresenter.destroy();
        Log.v(WowhqApplication.LOG_TAG, "onDestroy - Сервис");

    }

    @Override
    public void makeNotification(long current_price, String region) {
        Log.v(WowhqApplication.LOG_TAG, "==Уведомление, текущая цена: " + String.valueOf(current_price) +"==");
        //Toast.makeText(getApplicationContext(), "Йуху! Токен превзошел ваши ожидания: " + region + " - " + String.valueOf(current_price), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, WowTokenActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_arrow_up_24)
                .setContentTitle(getResources().getText(R.string.token_notification_title))
                .setContentText(getResources().getText(R.string.token_notification_text) + region + " - " + String.valueOf(current_price))
                .setTicker(getResources().getText(R.string.token_notification_text) + region + " - " + String.valueOf(current_price))
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true);
        mNotificationManager.notify(NOTIFICATION_ID,builder.build());
    }

    /**
     * Если Android 8.0+ то нужно устанавливать канал
     */
    public void initChannel() {
        if (Build.VERSION.SDK_INT < 26)
            return;

        NotificationChannel defaultChannel = new NotificationChannel(CHANNEL,
                "DEFAULT CHANNEL, WOWTOKEN", NotificationManager.IMPORTANCE_DEFAULT);
        defaultChannel.setVibrationPattern(VIBRATION_PATTERN);
        defaultChannel.enableVibration(true);
        mNotificationManager.createNotificationChannel(defaultChannel);
    }

    @Override
    public void stopService() {
        stopSelf();
    }
}
