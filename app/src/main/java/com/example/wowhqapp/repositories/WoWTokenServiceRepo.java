package com.example.wowhqapp.repositories;

import android.util.Log;

import com.example.wowhqapp.WowhqApplication;
import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.databases.dao.WoWTokenDao;
import com.example.wowhqapp.databases.database.AucAndTokenDataBase;
import com.example.wowhqapp.databases.entity.WoWToken;
import com.example.wowhqapp.network.AucAndTokenNetwork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WoWTokenServiceRepo implements MainContract.TokenServiceRepository {

    private WoWTokenDao mWoWTokenDao;
    private String mRegion;
    private long current_price;


    public WoWTokenServiceRepo(String region){
        mRegion = region;
        mWoWTokenDao = AucAndTokenDataBase.getDB().getWoWTokenDao();
        current_price = 0;
    }

    @Override
    public long saveWoWTokenAndGetCurrentPrice() {
        Log.v(WowhqApplication.LOG_TAG, "saveWoWTokenAndGetCurrentPrice");

        AucAndTokenNetwork.getInstance().getWoWTokenAPI().getWoWTokenByRegion(mRegion).enqueue(
                new Callback<WoWToken>() {
                    @Override
                    public void onResponse(Call<WoWToken> call, Response<WoWToken> response) {
                        Log.v(WowhqApplication.LOG_TAG, "onResponse - получаем WoWToken");

                        WoWToken woWToken = response.body();
                        Log.v(WowhqApplication.LOG_TAG, "Данные полученные ИЗ СЕТИ: Регион: " + woWToken.getRegion() + "Текущая цена: " + woWToken.getCurrentPriceBlizzardApi());

                        //Test code::
                        try {
                            assert woWToken != null;
                            woWToken.setCurrentPriceBlizzardApi(Double.valueOf(woWToken.getCurrentPriceBlizzardApi().doubleValue()*(Math.random()+0.7)).longValue());
                            if (Math.random() < 0.5){
                                woWToken.setLastChange(-woWToken.getLastChange());
                            }
                            woWToken.setLastChange(Double.valueOf(woWToken.getLastChange().doubleValue()*(Math.random()+0.8)).longValue());
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        Log.v(WowhqApplication.LOG_TAG, "Данные токена, МОДИФИЦИРОВАННЫЕ: Регион: " + woWToken.getRegion() + "Текущая цена: " + woWToken.getCurrentPriceBlizzardApi());
                        mWoWTokenDao.insert(woWToken);
                        setCurrentPice(woWToken.getCurrentPriceBlizzardApi());
                    }

                    @Override
                    public void onFailure(Call<WoWToken> call, Throwable t) {
                        Log.v(WowhqApplication.LOG_TAG, "Ошибка при запросе токена по API соеинения");
                    }
                }
        );
        //test_code:
//        Random random = new Random();
//        int i = random.nextInt(100 + 1) +1;
//        long num = 112*i;
//        WoWToken woWToken = new WoWToken("eu", "2019", num,num,num,num);
//        mWoWTokenDao.insert(woWToken);
//        long current_price = woWToken.getCurrentPriceBlizzardApi();
        Log.v(WowhqApplication.LOG_TAG, "[NOTYFICATION_DEBUG]Текущая цена по версии Repo (Service): "+String.valueOf(current_price));
        return current_price;
    }
    private void setCurrentPice(long currentPice){
        current_price = currentPice;
        Log.v(WowhqApplication.LOG_TAG, "[NOTYFICATION_DEBUG]Текущая цена по версии Repo, при попытке установить ее в поле (Service): "+String.valueOf(current_price));

    }
}
