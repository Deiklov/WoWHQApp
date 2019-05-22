package com.example.wowhqapp.presenters;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.repositories.SettingRepository;

public class AuctionsPresenter implements MainContract.AuctionsPresenter {

    private SettingRepository mSettingRepository;
    private MainContract.AuctionsView mAuctionsView;
    private Boolean mTypeOfActivity;

    public AuctionsPresenter(MainContract.AuctionsView auctionsView, SettingRepository settingRepository){
        mAuctionsView = auctionsView;
        mSettingRepository = settingRepository;
    }

    @Override
    public void init(Boolean type, String[] titles) {
        mTypeOfActivity = type;
        mAuctionsView.setTitle(mTypeOfActivity ? titles[0] : titles[1] + " - " + mSettingRepository.getSlug());
//        mAuctionsView.setFragment(type);
    }

    @Override
    public void MenuItemSelected() {
        mAuctionsView.closeAuctions();
    }
}
