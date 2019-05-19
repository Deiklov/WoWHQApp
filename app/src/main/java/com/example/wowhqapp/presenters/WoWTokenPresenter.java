package com.example.wowhqapp.presenters;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.repositories.SettingRepository;
import com.example.wowhqapp.repositories.WoWTokenRepo;

public class WoWTokenPresenter implements MainContract.WoWTokenPresenter {

    private SettingRepository mSettingRepository;
    private MainContract.WoWTokenView mWoWTokenView;
    private MainContract.TokenRepository mTokenRepository;

    public WoWTokenPresenter(SettingRepository settingRepository, MainContract.WoWTokenView woWTokenView){
        mSettingRepository = settingRepository;
        mWoWTokenView = woWTokenView;
        mTokenRepository = new WoWTokenRepo(mSettingRepository.getRegion(), this);
    }

    @Override
    public void init() {
        mWoWTokenView.setPrice(mTokenRepository.getMin(), mTokenRepository.getMax(), mTokenRepository.getCurrent());
       //mWoWTokenView.setPrice();
    }

    @Override
    public void test_updateToken(int coef) {
        mTokenRepository.test_insertToken(coef);

    }

    @Override
    public void setServiceStatus(Boolean val) {

    }

    @Override
    public void destroy() {
        mTokenRepository.destroy();
    }
}
