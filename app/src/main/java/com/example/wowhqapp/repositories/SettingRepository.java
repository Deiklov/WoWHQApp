package com.example.wowhqapp.repositories;

import android.content.SharedPreferences;

import com.example.wowhqapp.contracts.MainContract;

public class SettingRepository implements MainContract.SettingRepository {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    public static final String APP_PREFERENCES = "App_setting";
    private static final String SLUG = "Slug";
    private static final String REGION = "Region";
    private static final String LANG = "Language";
    private static final String WoWTokenServiceEnable = "WoWTokenServiceEnable"; //Отвечает за следующее: слать ли уведомления, соответственно запускаться ли самостоятельно , рисование галочки
    private static final String TARGET_PRICE = "WoWTokenTargetPrice";
    private static final String TARGET_PRICE_SIGN = "WoWTokenTargetPrice_Sign";






    public SettingRepository(SharedPreferences preferences){
        mPreferences = preferences;
        mEditor = mPreferences.edit();
    }

    @Override
    public String getSlug() {
        String value = mPreferences.getString(SLUG, "no slug");
        return value;
    }

    @Override
    public String getRegion() {
        String value = mPreferences.getString(REGION, "eu");
        return value;
    }

    @Override
    public String getLang() {
        String value = mPreferences.getString(LANG, "no lang");
        return value;
    }

    @Override
    public Boolean getWoWTokenServiceEnable() {
        Boolean value = mPreferences.getBoolean(WoWTokenServiceEnable, false);
        //Log.v(WowhqApplication.LOG_TAG, "Нужны ли уведомления (чек бокс): "+String.valueOf(value));
        return value;
    }

    @Override
    public Boolean getTargetPriceSig() {
        Boolean value = mPreferences.getBoolean(TARGET_PRICE_SIGN, false);
        return value;
    }

    @Override
    public long getTargetPrice() {
        long value = mPreferences.getLong(TARGET_PRICE, 123321);
        return value;
    }

    @Override
    public void setTargetPrice(long val) {
        mEditor.putLong(TARGET_PRICE, val);
        mEditor.apply();
    }

    @Override
    public void setTargetPriceSig(Boolean val) {
        mEditor.putBoolean(TARGET_PRICE_SIGN, val);
        mEditor.apply();
    }

    @Override
    public void setWoWTokenServiceEnable(Boolean val) {
        mEditor.putBoolean(WoWTokenServiceEnable, val);
        mEditor.apply();
    }

    @Override
    public void setSlug(String value) {
        mEditor.putString(SLUG, value);
        mEditor.apply();
    }

    @Override
    public void setRegion(String value) {
        mEditor.putString(REGION, value);
        mEditor.apply();
    }

    @Override
    public void setLang(String value) {
        mEditor.putString(LANG, value);
        mEditor.apply();
    }
}
