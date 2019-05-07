package com.example.wowhqapp;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.wowhqapp.databases.database.TalentsCalculatorDatabase;

public class WowhqApplication extends Application {
    private TalentsCalculatorDatabase mTalentCalcDb;
    // private ApiRepo mApiRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        // mApiRepo = new ApiRepo();
        mTalentCalcDb = Room.databaseBuilder(
                this,
                TalentsCalculatorDatabase.class,
                "talents_calculator_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public TalentsCalculatorDatabase getTalentsCalculatorDatabase() {
        return mTalentCalcDb;
    }

    // public ApiRepo getApis() {
    //     return mApiRepo;
    // }

    public static WowhqApplication from(Context context) {
        return (WowhqApplication) context.getApplicationContext();
    }
}
