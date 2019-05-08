package com.example.wowhqapp.presenters;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.repositories.SettingRepository;

public class WoWTokenPresenter implements MainContract.WoWTokenPresenter {

    private SettingRepository mSettingRepository;
    private MainContract.WoWTokenView mWoWTokenView;

    public WoWTokenPresenter(SettingRepository settingRepository, MainContract.WoWTokenView woWTokenView){
        mSettingRepository = settingRepository;
        mWoWTokenView = woWTokenView;

    }

    @Override
    public void init() {
        mWoWTokenView.setPrice();
    }

    @Override
    public void setServiceStatus(Boolean val) {

    }
}
