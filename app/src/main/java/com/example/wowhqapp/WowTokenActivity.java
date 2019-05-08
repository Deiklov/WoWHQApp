package com.example.wowhqapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.presenters.WoWTokenPresenter;
import com.example.wowhqapp.repositories.SettingRepository;

import org.w3c.dom.Text;

public class WowTokenActivity extends AppCompatActivity implements MainContract.WoWTokenView {

    private CheckBox mCheckBox;
    private TextView mCurrentText;
    private TextView mMinText;
    private TextView mMaxText;
    private WoWTokenPresenter mWoWTokenPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wow_token);

        mWoWTokenPresenter = new WoWTokenPresenter(new SettingRepository(getSharedPreferences(SettingRepository.APP_PREFERENCES, Context.MODE_PRIVATE)), this);

        mCheckBox = (CheckBox) findViewById(R.id.token_start);
        mCurrentText = (TextView) findViewById(R.id.token_current);
        mMaxText = (TextView) findViewById(R.id.token_max);
        mMinText = (TextView) findViewById(R.id.token_min);

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

    }

    @Override
    public void setPrice(int min, int max, int current) {
        mMinText.setText(getResources().getString(R.string.token_min) + " " +Integer.toString(min));
        mMaxText.setText(getResources().getString(R.string.token_max) + " " +Integer.toString(max));
        mCurrentText.setText(getResources().getString(R.string.token_current) + " " +Integer.toString(current));
    }

    @Override
    public void setBox(Boolean val) {
        mCheckBox.setChecked(val);
    }
}
