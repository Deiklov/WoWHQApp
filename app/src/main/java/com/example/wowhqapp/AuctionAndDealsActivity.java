package com.example.wowhqapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.presenters.AuctionsPresenter;
import com.example.wowhqapp.repositories.SettingRepository;

public class AuctionAndDealsActivity extends AppCompatActivity implements MainContract.AuctionsView {

    private AuctionsPresenter mAuctionsPresenter;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_and_deals);

        mToolbar = (Toolbar) findViewById(R.id.auctions_toolbar);

        mAuctionsPresenter = new AuctionsPresenter(this, new SettingRepository(getSharedPreferences(SettingRepository.APP_PREFERENCES, Context.MODE_PRIVATE)));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.auctions_a_bar);
        mToolbarTitle = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.auciton_action_bar_title);

        mAuctionsPresenter.init(getIntent().getExtras().getBoolean(MenuActivity.KEY_TYPE), getResources().getStringArray(R.array.auction_title));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAuctionsPresenter.MenuItemSelected();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.auction_menu, menu);
        return true;
    }



    @Override
    public void closeAuctions() {
        this.finish();
    }

    @Override
    public void setTitle(String txt) {
        mToolbarTitle.setText(txt);
    }

    @Override
    public void setFragment(Boolean type) {
        Fragment auctions_frame_l = getSupportFragmentManager().findFragmentById(R.id.auctions_frame_lay);
        if (auctions_frame_l == null) {
            AuctionsFragment auctionsFragment = new AuctionsFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(AuctionsFragment.KEY_TYPE, type);
            auctionsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.auctions_frame_lay, auctionsFragment).commit();
        }
    }
}
