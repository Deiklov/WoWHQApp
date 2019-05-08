package com.example.wowhqapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;


public class WoWTokenService extends IntentService {


    public WoWTokenService() {
        super("WoWTokenService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }


}
