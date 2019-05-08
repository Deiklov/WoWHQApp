package com.example.wowhqapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wowhqapp.repositories.SettingRepository;

public class AuctionAndDealsActivity extends AppCompatActivity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_and_deals);

        final SettingRepository rep = new SettingRepository(getSharedPreferences(SettingRepository.APP_PREFERENCES, Context.MODE_PRIVATE));

        mTextView = (TextView) findViewById(R.id.txtView);
        mTextView.setText(getIntent().getExtras().getBoolean(MenuActivity.KEY_TYPE) ? "Аукционы" : "Сделки");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("INFOOOO", rep.getSlug());
                Toast.makeText(getApplicationContext(), "ssss", Toast.LENGTH_LONG).show();

            }
        });
    }
}
