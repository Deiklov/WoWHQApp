package com.example.wowhqapp.repositories;

import android.content.SharedPreferences;

import com.example.wowhqapp.contracts.MainContract;

public class SettingRepository implements MainContract.SettingRepository {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    public static final String APP_PREFERENCES = "App_setting";
    public static final String SLUG = "Slug";
    public static final String REGION = "Region";
    public static final String LANG = "Language";
    public static final String WoWTokenServiceEnable = "WoWTokenServiceEnable";




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
        String value = mPreferences.getString(REGION, "no region");
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
        return value;
    }

    @Override
    public void setWoWTokenServiceEnable(Boolean val) {
        mEditor.putBoolean(WoWTokenServiceEnable, val);
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
