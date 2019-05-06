package com.example.wowhqapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AuctionAndDealsActivity extends AppCompatActivity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_and_deals);
        mTextView = (TextView) findViewById(R.id.txtView);
        mTextView.setText(getIntent().getExtras().getBoolean(MenuActivity.KEY_TYPE) ? "Аукционы" : "Сделки");

    }
}
