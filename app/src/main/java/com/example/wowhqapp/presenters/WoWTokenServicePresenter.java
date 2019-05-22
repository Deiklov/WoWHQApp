package com.example.wowhqapp.presenters;

import android.util.Log;

import com.example.wowhqapp.WowhqApplication;
import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.repositories.SettingRepository;
import com.example.wowhqapp.repositories.WoWTokenServiceRepo;

import java.util.Timer;
import java.util.TimerTask;

public class WoWTokenServicePresenter implements MainContract.WoWTokenServicePresenter {


    private SettingRepository mSettingRepository;
    private MainContract.WoWTokenServiceView mWoWTokenServiceView;
    private MainContract.TokenServiceRepository mTokenServiceRepository;
    private boolean isThreadWork;
    private long last_notification_price;
    private long target_price;
    private boolean target_price_sign;
    private boolean is_check_target_price;
    private Timer mTokenScanerTimer;


    public WoWTokenServicePresenter(SettingRepository settingRepository, MainContract.WoWTokenServiceView woWTokenServiceView){
        mSettingRepository = settingRepository;
        mWoWTokenServiceView = woWTokenServiceView;
        mTokenServiceRepository = new WoWTokenServiceRepo(mSettingRepository.getRegion());

        //Больше/Меньше
        target_price_sign = mSettingRepository.getTargetPriceSig();
        //Желаемая цена
        target_price = mSettingRepository.getTargetPrice();
        //Чтобы не слать одинаковые уведомления
        last_notification_price = 0;
        //Т.к. если что-то изменится то произойдет рестарт сервиса и значение обновится
        is_check_target_price = mSettingRepository.getWoWTokenServiceEnable();
        //Запихнул его сюда т.к. нужно его еще и завершать
        mTokenScanerTimer = new Timer();
    }

    @Override
    public void init(boolean is_from_activity) {
        if ((is_from_activity || mSettingRepository.getWoWTokenServiceEnable()) && !isThreadWork){ //Проверяем можноли работать сервису
            isThreadWork = true;
            mTokenScanerTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    scanWoWToken();
                }
            }, 100L, 8L*1000);
        }
        else{
            mWoWTokenServiceView.stopService();
        }
    }

    @Override
    public void destroy() {
        mTokenScanerTimer.cancel();
    }

    private void scanWoWToken(){
        Log.v(WowhqApplication.LOG_TAG, "Вызван scanWoWToken");

        long current_price = mTokenServiceRepository.saveWoWTokenAndGetCurrentPrice();
        Log.v(WowhqApplication.LOG_TAG, "[NOTYFICATION_DEBUG]Текущая цена по версии Presenter (Service): "+String.valueOf(current_price));

        //Смотрим, делать ли уведомление
        if (is_check_target_price && (last_notification_price != current_price) &&
                ((target_price > current_price && !target_price_sign)
                        || (target_price < current_price && target_price_sign)
                        || target_price == current_price)){

            Log.v(WowhqApplication.LOG_TAG, "[NOTYFICATION_DEBUG]scanWoWToken одобрил отправку уведомления");
            mWoWTokenServiceView.makeNotification(current_price, mSettingRepository.getRegion());
            last_notification_price = current_price;
        }
    }
}
