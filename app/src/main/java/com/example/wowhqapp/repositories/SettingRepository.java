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


    private static final String TALENTS_LANG = "Talents_language";
    private static final String TALENTS_WOWCLASS_ID = "Talents_wowClass_id";
    private static final String TALENTS_WOWSPEC_ID = "Talents_wowSpec_id";
    private static final String TALENTS_WOWSPEC_ORDER = "Talents_wowSpec_order";
    private static final String TALENTS_WOWTALENT_ID = "Talents_wowTalent_id";



    public SettingRepository(SharedPreferences preferences){
        mPreferences = preferences;
        mEditor = mPreferences.edit();
        mEditor.apply();
    }

    @Override
    public String getSlug() {
        return mPreferences.getString(SLUG, "no slug");
    }

    @Override
    public void setSlug(String value) {
        mEditor.putString(SLUG, value);
        mEditor.apply();
    }

    @Override
    public String getRegion() {
        return mPreferences.getString(REGION, "eu");
    }

    @Override
    public void setRegion(String value) {
        mEditor.putString(REGION, value);
        mEditor.apply();
    }

    @Override
    public String getLang() {
        return mPreferences.getString(LANG, "no lang");
    }

    @Override
    public void setLang(String value) {
        mEditor.putString(LANG, value);
        mEditor.apply();
    }

    @Override
    public String getTalentsLang() {
        return mPreferences.getString(TALENTS_LANG, "ru");
    }

    @Override
    public void setTalentsLang(String value) {
        mEditor.putString(TALENTS_LANG, value);
        mEditor.apply();
    }

    @Override
    public int getTalentsWowClassId() {
        return mPreferences.getInt(TALENTS_WOWCLASS_ID, -1);
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
    public void setTalentsWowClassId(int wowClassId) {
        mEditor.putInt(TALENTS_WOWCLASS_ID, wowClassId);
        mEditor.apply();
    }

    @Override
    public int getTalentsWowSpecId() {
        return mPreferences.getInt(TALENTS_WOWSPEC_ID, -1);
    }

    @Override
    public void setTalentsWowSpecId(int wowSpecId) {
        mEditor.putInt(TALENTS_WOWSPEC_ID, wowSpecId);
        mEditor.apply();
    }

    @Override
    public int getTalentsWowSpecOrder() {
        return mPreferences.getInt(TALENTS_WOWSPEC_ORDER, -1);
    }

    @Override
    public void setTalentsWowSpecOrder(int wowSpecOrder) {
        mEditor.putInt(TALENTS_WOWSPEC_ORDER, wowSpecOrder);
        mEditor.apply();
    }

    @Override
    public int getTalentsWowTalentId() {
        return mPreferences.getInt(TALENTS_WOWTALENT_ID, -1);
    }

    @Override
    public void setTalentsWowTalentId(int wowTalentId) {
        mEditor.putInt(TALENTS_WOWTALENT_ID, wowTalentId);
        mEditor.apply();
    }
}
