package com.example.wowhqapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.presenters.WoWTokenPresenter;
import com.example.wowhqapp.repositories.SettingRepository;

public class WowTokenActivity extends AppCompatActivity implements MainContract.WoWTokenView {

    private CheckBox mCheckBox;
    private TextView mCurrentText;
    private TextView mMinText;
    private TextView mMaxText;
    private WoWTokenPresenter mWoWTokenPresenter;


    private EditText mTestEditTExt;
    private Button mTestBtn;
    private Button mTestBtn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wow_token);

        mTestEditTExt = (EditText) findViewById(R.id.test_coef_et);
        mTestBtn = (Button) findViewById(R.id.test_btn);
        mTestBtn2 = (Button) findViewById(R.id.test_btn2);

        mTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWoWTokenPresenter.test_updateToken(Integer.parseInt(mTestEditTExt.getText().toString()));
                //mWoWTokenPresenter.init();
            }
        });
        mTestBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWoWTokenPresenter.init();
            }
        });

        mWoWTokenPresenter = new WoWTokenPresenter(new SettingRepository(getSharedPreferences(SettingRepository.APP_PREFERENCES, Context.MODE_PRIVATE)), this);

        mCheckBox = (CheckBox) findViewById(R.id.token_start);
        mCurrentText = (TextView) findViewById(R.id.token_current);
        mMaxText = (TextView) findViewById(R.id.token_max);
        mMinText = (TextView) findViewById(R.id.token_min);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                //mWoWTokenPresenter.init();

            }
        }, 3000);

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("REPO_TOKEN", "destroy");

        mWoWTokenPresenter.destroy();
    }

    @Override
    public void setPrice(long min, long max, long current) {
        mMinText.setText(getResources().getString(R.string.token_min) + " " +Long.toString(min));
        mMaxText.setText(getResources().getString(R.string.token_max) + " " +Long.toString(max));
        mCurrentText.setText(getResources().getString(R.string.token_current) + " " +Long.toString(current));
    }

    @Override
    public void setBox(Boolean val) {
        mCheckBox.setChecked(val);
    }
}
