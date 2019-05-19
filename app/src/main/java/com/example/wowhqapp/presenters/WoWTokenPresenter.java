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
    public void init_price() {
        mWoWTokenView.setPrice(mTokenRepository.getMin(), mTokenRepository.getMax(), mTokenRepository.getCurrent(),
                mTokenRepository.getlastChange(), (mTokenRepository.getlastChange() > 0) ? 0 : 1);
    }

    @Override
    public void init_traget_price() {
        mWoWTokenView.setBox(mSettingRepository.getWoWTokenServiceEnable());
        mWoWTokenView.setRadioBtn(mSettingRepository.getTargetPriceSig());
        mWoWTokenView.setTargetPriceEditText(mSettingRepository.getTargetPrice());
    }

    @Override
    public void test_updateToken(int coef) {
        mTokenRepository.test_insertToken(coef);

    }

    @Override
    public void setServiceStatus(Boolean val) {
        mSettingRepository.setWoWTokenServiceEnable(val);
    }

    @Override
    public void setTargetPrice(Boolean val, long price) {
        mSettingRepository.setTargetPrice(price);
        mSettingRepository.setTargetPriceSig(val);
    }

    @Override
    public void destroy() {
        mTokenRepository.destroy();
    }
}
