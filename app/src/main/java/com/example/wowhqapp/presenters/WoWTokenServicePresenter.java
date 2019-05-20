package com.example.wowhqapp.presenters;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.repositories.SettingRepository;
import com.example.wowhqapp.repositories.WoWTokenServiceRepo;

import java.util.concurrent.TimeUnit;

public class WoWTokenServicePresenter implements MainContract.WoWTokenServicePresenter {


    private SettingRepository mSettingRepository;
    private MainContract.WoWTokenServiceView mWoWTokenServiceView;
    private MainContract.TokenServiceRepository mTokenServiceRepository;


    public WoWTokenServicePresenter(SettingRepository settingRepository, MainContract.WoWTokenServiceView woWTokenServiceView){
        mSettingRepository = settingRepository;
        mWoWTokenServiceView = woWTokenServiceView;
        mTokenServiceRepository = new WoWTokenServiceRepo();
    }

    @Override
    public void init(boolean is_from_activity) {
        if (is_from_activity || mSettingRepository.getWoWTokenServiceEnable()){
            scanWoWToken(mSettingRepository.getWoWTokenServiceEnable());
        }
    }

    private void scanWoWToken(boolean is_check_target_price){
        boolean target_price_sign = mSettingRepository.getTargetPriceSig();
        long target_price = mSettingRepository.getTargetPrice();
        long last_notification_price = 0; //Чтобы не слать одинаковые уведомления
        while (true){
            long current_price = mTokenServiceRepository.saveWoWTokenAndGetCurrentPrice();
            if (is_check_target_price && (last_notification_price != current_price) &&
                    ((target_price < current_price && !target_price_sign)
                    || (target_price > current_price && target_price_sign)
                    || target_price == current_price)){
                mWoWTokenServiceView.makeNotification(target_price);
                last_notification_price = current_price;
            }
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
