package com.example.wowhqapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TalantsAndBuildsActivity extends AppCompatActivity {

    public String TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talants_and_builds);
        TYPE = getIntent().getExtras().getBoolean(MenuActivity.KEY_TYPE) ? "Таблица талантов" : "Сборки талантов";
    }
}
