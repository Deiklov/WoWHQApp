package com.example.wowhqapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.databases.entity.Lot;
import com.example.wowhqapp.fragments.auctions.AuctionListFragment;
import com.example.wowhqapp.presenters.AuctionsPresenter;
import com.example.wowhqapp.repositories.SettingRepository;

import java.util.List;

public class AuctionAndDealsActivity extends AppCompatActivity implements MainContract.AuctionsView, AuctionListFragment.OnListFragmentInteractionListener {

    private AuctionsPresenter mAuctionsPresenter;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private Button mLoadNewDataButton;
    private AuctionListFragment mAuctionsFragment;
    private ContentLoadingProgressBar mContentLoadingProgressBar;
    private ConstraintLayout mConstraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_and_deals);

        mAuctionsFragment = new AuctionListFragment();
//        Fragment auctions_frament = getSupportFragmentManager().findFragmentById(R.id.auctions_frame_lay);
//        if (auctions_frament == null) {
//            mAuctionsFragment = new AuctionListFragment();
//        }

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
            if (!type){
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                mConstraintLayout = (ConstraintLayout) layoutInflater.inflate(R.layout.load_btn, null, false);
                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.auctions_frame_lay_load_more_btn);
                frameLayout.addView(mConstraintLayout);
                mLoadNewDataButton = (Button) mConstraintLayout.findViewById(R.id.auctions_load_btn);
                mLoadNewDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAuctionsPresenter.onLoadNewDataBtnClick();
                    }
                });
                Log.v(WowhqApplication.LOG_TAG, "[LOAD NEW AUCS BTN] add constraintLayout");

            }
        }

    @Override
    public void initAdapter(List<Lot> lotList) {
        mAuctionsFragment.initAdapter(lotList);

    }

    @Override
    public void notifyAuctionsChange() {
        mAuctionsFragment.notifyAuctionsChange();
    }

    @Override
    public void showLoadNewDataBtn() {
        mConstraintLayout.animate()
                .alpha(1.0f)
                .setDuration(50)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mLoadNewDataButton.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void hideLoadNewDataBtn() {
        mConstraintLayout.animate()
                .alpha(0.0f)
                .setDuration(50)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mLoadNewDataButton.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void showErrorView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        ConstraintLayout constraintLayout = (ConstraintLayout) layoutInflater.inflate(R.layout.auction_error_layout, null, false);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.auctions_frame_lay_error);
        frameLayout.addView(constraintLayout);
    }

    @Override
    public void addProgressBar() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        ConstraintLayout constraintLayout = (ConstraintLayout) layoutInflater.inflate(R.layout.loading_view, null, false);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.auctions_frame_lay_loading_view);
        frameLayout.addView(constraintLayout);
        mContentLoadingProgressBar = (ContentLoadingProgressBar) constraintLayout.findViewById(R.id.auctions_LoadingProgressBar);
    }

    @Override
    public void showProgressBar() {
        mContentLoadingProgressBar.show();
    }

    @Override
    public void hideProgressBar() {
        mContentLoadingProgressBar.hide();
    }

    @Override
    public void onScrollDown() {
        Log.v(WowhqApplication.LOG_TAG, "[LOAD NEW AUCS BTN] onScrollDown");
        mAuctionsPresenter.onScrollDown();
    }

    @Override
    public void onScrollUp() {
        mAuctionsPresenter.onScrollUp();
    }

    @Override
    public void onScrollDownNotToEnd() {
        mAuctionsPresenter.onScrollDownNotToEnd();
    }


    @Override
    public void onListFragmentInteraction(Lot lot) {
        Log.v(WowhqApplication.LOG_TAG, "onListFragmentInteraction - это обработчик нажатия на элемент списка (Лот), AuctionAndDeals Activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuctionsPresenter.onDestroy();
    }
}
