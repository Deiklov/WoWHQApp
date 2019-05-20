package com.example.wowhqapp.repositories;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.databases.dao.WoWTokenDao;
import com.example.wowhqapp.databases.database.AucAndTokenDataBase;
import com.example.wowhqapp.databases.entity.WoWToken;

import java.util.Random;

public class WoWTokenServiceRepo implements MainContract.TokenServiceRepository {

    private WoWTokenDao mWoWTokenDao;


    public WoWTokenServiceRepo(){
        mWoWTokenDao = AucAndTokenDataBase.getDB().getWoWTokenDao();
    }

    @Override
    public long saveWoWTokenAndGetCurrentPrice() {
        //test_code:
        Random random = new Random();
        int i = random.nextInt(100 + 1) +1;
        long num = 112*i;
        WoWToken woWToken = new WoWToken("eu", "2019", num,num,num,num);
        mWoWTokenDao.insert(woWToken);
        long current_price = woWToken.getCurrentPriceBlizzardApi();
        return current_price;
    }
}
