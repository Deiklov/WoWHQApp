package com.example.wowhqapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wowhqapp.repositories.SettingRepository;
import com.example.wowhqapp.presenters.SettingPresenter;

public class SettingActivity extends AppCompatActivity {

    private SettingPresenter mSettingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mSettingPresenter = new SettingPresenter(new SettingRepository(getSharedPreferences(SettingRepository.APP_PREFERENCES, Context.MODE_PRIVATE)));
    }
}
