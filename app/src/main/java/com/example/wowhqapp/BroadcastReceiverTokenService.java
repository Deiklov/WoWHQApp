package com.example.wowhqapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastReceiverTokenService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(WoWTokenService.ACTION_START);
        context.startActivity(i);
    }
}
