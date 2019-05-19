package com.example.wowhqapp.repositories;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.wowhqapp.contracts.MainContract;

public class SettingRepository implements MainContract.SettingRepository {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    public static final String APP_PREFERENCES = "App_setting";
    public static final String SLUG = "Slug";
    public static final String REGION = "Region";
    public static final String LANG = "Language";
    public static final String WoWTokenServiceEnable = "WoWTokenServiceEnable";
    public static final String TARGET_PRICE = "WoWTokenTargetPrice";
    public static final String TARGET_PRICE_SIGN = "WoWTokenTargetPrice_Sign";






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
        Log.v("REPO_TOKEN", "Забираем статут чек бокса из SettingRepo: "+ String.valueOf(value));
        return value;
    }

    @Override
    public Boolean getTargetPriceSig() {
        Boolean value = mPreferences.getBoolean(TARGET_PRICE_SIGN, false);
        Log.v("REPO_TOKEN", "Забираем ЗНАК ТАРГЕТ ЦЕНЫ из SettingRepo: " + String.valueOf(value));

        return value;
    }

    @Override
    public long getTargetPrice() {
        long value = mPreferences.getLong(TARGET_PRICE, 123321);
        Log.v("REPO_TOKEN", "Забираем ТАРГЕТ ЦЕНУ из SettingRepo: " + String.valueOf(value));

        return value;
    }

    @Override
    public void setTargetPrice(long val) {
        Log.v("REPO_TOKEN", "Устанавливаем ТАРГЕТ ЦЕНУ из SettingRepo: " + String.valueOf(val));

        mEditor.putLong(TARGET_PRICE, val);
        mEditor.apply();
    }

    @Override
    public void setTargetPriceSig(Boolean val) {
        Log.v("REPO_TOKEN", "Устанавливаем ЗНАК ТАРГЕТ ЦЕНЫ из SettingRepo: " + String.valueOf(val));
        mEditor.putBoolean(TARGET_PRICE_SIGN, val);
        mEditor.apply();
    }

    @Override
    public void setWoWTokenServiceEnable(Boolean val) {
        Log.v("REPO_TOKEN", "Устанавливаем статут чек бокса из SettingRepo: " + String.valueOf(val));

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
