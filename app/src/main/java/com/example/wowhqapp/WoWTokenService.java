package com.example.wowhqapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.presenters.WoWTokenServicePresenter;
import com.example.wowhqapp.repositories.SettingRepository;


public class WoWTokenService extends Service implements MainContract.WoWTokenServiceView {

    public static final String ACTION = "TOKEN_SERVICE";
    public static final String ACTION_STOP = BuildConfig.APPLICATION_ID + "_STOP";
    public static final String IS_FROM_ACTICITY = BuildConfig.APPLICATION_ID + "_IS_FROM_ACTIVITY";

    private MainContract.WoWTokenServicePresenter mWoWTokenServicePresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(WowhqApplication.LOG_TAG, "onCreate - Сервис");
        mWoWTokenServicePresenter = new WoWTokenServicePresenter(new SettingRepository(getSharedPreferences(SettingRepository.APP_PREFERENCES, Context.MODE_PRIVATE)),this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(WowhqApplication.LOG_TAG, "onStartCommand - Сервис");
        mWoWTokenServicePresenter.init((intent !=null) && intent.getExtras().getBoolean(IS_FROM_ACTICITY, false));

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
    public void makeNotification(long current_price) {
        Log.v(WowhqApplication.LOG_TAG, "==Уведомление, текущая цена: " + String.valueOf(current_price) +"==");
    }

    @Override
    public void stopService() {
        stopSelf();
    }
}
