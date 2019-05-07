package com.example.wowhqapp.presenters;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.MenuActivity;
import com.example.wowhqapp.repositories.SettingRepository;

public class SettingPresenter implements MainContract.SettingPresenter {

    private SettingRepository mSettingRepository;
    private MainContract.MenuView mMenuView;
    private MainContract.SettingView mSettingView;

    public SettingPresenter(SettingRepository repository, MainContract.SettingView settingView){
        mSettingRepository = repository;
        mMenuView = new MenuActivity();
        mSettingView = settingView;
    }

    @Override
    public String getSlug() {
        String slug = mSettingRepository.getSlug();
        if (slug == "no slug"){
            mMenuView.openSettingActivity();
        }
        return slug;
    }

    @Override
    public String getRegion() {
        String region = mSettingRepository.getRegion();
        if (region == "no region"){
            mMenuView.openSettingActivity();
        }
        return region;
    }

    @Override
    public String getLang() {
        String lang = mSettingRepository.getLang();
        if (lang == "no lang"){
            mMenuView.openSettingActivity();
        }
        return lang;
    }

    @Override
    public void setSlug(String value) {
        mSettingRepository.setSlug(value);
    }

    @Override
    public void setRegion(String value) {
        mSettingRepository.setRegion(value);
    }

    @Override
    public void setLang(String value) {
        mSettingRepository.setLang(value);
    }

    @Override
    public void MenuItemSelected() {
        mSettingView.CloseSetting();
    }

}
