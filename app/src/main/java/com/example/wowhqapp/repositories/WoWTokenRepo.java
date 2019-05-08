package com.example.wowhqapp.repositories;

import com.example.wowhqapp.contracts.MainContract;

public class WoWTokenRepo implements MainContract.TokenRepository {
    @Override
    public void setMinMaxCurrent() {

    }

    @Override
    public int getMax(String region) {
        return 0;
    }

    @Override
    public int getMin(String region) {
        return 0;
    }

    @Override
    public int getCurrent(String region) {
        return 0;
    }
}
