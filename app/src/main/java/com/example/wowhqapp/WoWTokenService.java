package com.example.wowhqapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.presenters.WoWTokenServicePresenter;
import com.example.wowhqapp.repositories.SettingRepository;


public class WoWTokenService extends Service implements MainContract.WoWTokenServiceView {

    public static final String ACTION_START = BuildConfig.APPLICATION_ID + "_START";
    public static final String ACTION_STOP = BuildConfig.APPLICATION_ID + "_STOP";
    public static final String IS_FROM_ACTICITY = BuildConfig.APPLICATION_ID + "_IS_FROM_ACTIVITY";

    private MainContract.WoWTokenServicePresenter mWoWTokenServicePresenter;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mWoWTokenServicePresenter = new WoWTokenServicePresenter(new SettingRepository(getSharedPreferences(SettingRepository.APP_PREFERENCES, Context.MODE_PRIVATE)),this);
        mWoWTokenServicePresenter.init((intent !=null) && intent.getExtras().getBoolean(IS_FROM_ACTICITY, false));
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void makeNotification(long current_price) {
        Log.v("REPO_TOKEN", "Типо уведомление, текущая цена: " + String.valueOf(current_price));
        Toast.makeText(getApplicationContext(), "Типо уведомление, текущая цена: " + String.valueOf(current_price), Toast.LENGTH_SHORT).show();
    }
}
