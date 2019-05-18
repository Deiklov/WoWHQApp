package com.example.wowhqapp;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.wowhqapp.databases.database.TalentsCalculatorDatabase;
import com.example.wowhqapp.network.ApiTalentsRepository;

public class WowhqApplication extends Application {
    private TalentsCalculatorDatabase mTalentCalcDb;
    private ApiTalentsRepository mApiTalentsRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        mApiTalentsRepo = new ApiTalentsRepository();
        mTalentCalcDb = Room.databaseBuilder(
                this,
                TalentsCalculatorDatabase.class,
                "talents_calculator_database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries() // TODO !!!
                .build();
    }

    public TalentsCalculatorDatabase getTalentsCalculatorDatabase() {
        return mTalentCalcDb;
    }

    public ApiTalentsRepository getTalentsApi() {
        return mApiTalentsRepo;
    }

    public static WowhqApplication from(Context context) {
        return (WowhqApplication) context.getApplicationContext();
    }
}
