package com.example.wowhqapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.databases.entity.SimpleLot;
import com.example.wowhqapp.fragments.auctions.AuctionListFragment;
import com.example.wowhqapp.fragments.auctions.dummy.DummyContent;
import com.example.wowhqapp.presenters.AuctionsPresenter;
import com.example.wowhqapp.repositories.SettingRepository;

import java.util.List;

public class AuctionAndDealsActivity extends AppCompatActivity implements MainContract.AuctionsView, AuctionListFragment.OnListFragmentInteractionListener {

    private AuctionsPresenter mAuctionsPresenter;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private AuctionListFragment mAuctionsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_and_deals);

        Fragment auctions_frament = getSupportFragmentManager().findFragmentById(R.id.auctions_frame_lay);
        if (auctions_frament == null) {
            mAuctionsFragment = new AuctionListFragment();
        }

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
            Bundle bundle = new Bundle();
            bundle.putBoolean(AuctionListFragment.KEY_TYPE, type);
            mAuctionsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out)
                    .add(R.id.auctions_frame_lay, mAuctionsFragment).commit();
        }

    @Override
    public void initAdapter(List<SimpleLot> simpleLotList) {
        mAuctionsFragment.initAdapter(simpleLotList);

    }

    @Override
    public void notifyAuctionsChange() {
        mAuctionsFragment.notifyAuctionsChange();
    }


    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Log.v(WowhqApplication.LOG_TAG, "onListFragmentInteraction - это обработчик нажатия на элемент списка (Лот), AuctionAndDeals Activity");
    }
}
