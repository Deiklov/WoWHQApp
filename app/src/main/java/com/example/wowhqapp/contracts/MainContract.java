package com.example.wowhqapp.contracts;

import com.example.wowhqapp.databases.entity.Lot;

import java.util.List;

public interface MainContract {

    interface MenuView{
        void openAuctionsActivity();
        void openDealsActivity();
        void openSettingActivity();
        void openSavedLotsActivity();
        void openWoWTokenActivity();
        void openTalantsActivity();
        void closeActivity();
    }
    interface MenuPresenter{
        void openAuctions();
        void openDeals();
        void openSavedLots();
        void openWoWToken();
        void openSetting();
        void closeMenu();
        void openTalants();
    }
    interface SettingRepository{
        String getSlug();
        String getRegion();
        String getLang();
        Boolean getWoWTokenServiceEnable();
        Boolean getTargetPriceSig();
        long getTargetPrice();
        void setTargetPrice(long val);
        void setTargetPriceSig(Boolean val);
        void setWoWTokenServiceEnable(Boolean val);
        void setSlug(String value);
        void setRegion(String value);
        void setLang(String value);

        String getTalentsLang();
        void setTalentsLang(String value);
        int getTalentsWowClassId();
        void setTalentsWowClassId(int wowClassId);
        int getTalentsWowSpecId();
        void setTalentsWowSpecId(int wowSpecId);
        int getTalentsWowSpecOrder();
        void setTalentsWowSpecOrder(int wowSpecOrder);
        int getTalentsWowTalentId();
        void setTalentsWowTalentId(int wowTalentId);
        String getTalentsActivityTitle();
        void setTalentsActivityTitle(String wowTalentId);

        void deleteAllSimpleLots();

        void setTokenActivityStatus(Boolean enable);
        Boolean getTokenActivityStatus();
    }
    interface SettingPresenter{
        void init();
        void onSlugSelect(String value);
        void onRegionSelect(String value);
        void onLangSelect(String value);
        void onMenuItemSelected();
    }
    interface SettingView{
        void SetSpinnerValues(String slug, String region, String lang);
        void SetSpinnerAdapter(String region);
        void CloseSetting();
    }

    //Auctions
    interface AuctionsPresenter{
        void init(Boolean type, String[] titles);
        void MenuItemSelected();
        void notifyUpdatedData();
        void notifyLittleData();
        void onDestroy();
        void onScrollDown();
        void onScrollDownNotToEnd();
        void onScrollUp();
        void onLoadNewDataBtnClick();
        void onGetFirstPageError();
        void onAllAuctionsDownload();
        String onGetGlideUrl(String icon_name); //Вообще не правильно так делать
    }
    interface AuctionsView{
        void closeAuctions();
        void setTitle(String txt);
        void setFragment(Boolean type);
        //Туда и в его коллегу передаем список лотов
        void initAdapter(List<Lot> lotList);
        void notifyAuctionsChange();
        void showLoadNewDataBtn();
        void hideLoadNewDataBtn();
        void showErrorView();
        void addProgressBar();
        void showProgressBar();
        void hideProgressBar();
    }

    interface AuctionsListFragView{
        void initAdapter(List<Lot> lotList);
        void notifyAuctionsChange();
    }

    interface AuctionsRepo{
        List<Lot> getLots();
        void resetLots();
        void downloadLots(int page);
        void deleteAllLots();
        void destroy();

    }


    //WoWToken
    interface WoWTokenView{
        void startJob();
        void setPrice(long min, long max, long current, long lastChange, int icon);
        void setBox(Boolean val);
        void setRadioBtn(Boolean val);
        void setTargetPriceEditText(long price);
        void closeWoWToken();


    }
    interface WoWTokenPresenter{
        void initPrice();
        void initTargetPriceAndStartJob();
        void test_updateToken(int coef);
        void setServiceStatus(Boolean val);
        void setTargetPrice(Boolean val, long price);
        void destroy();
        void onStop();
        void onMenuItemSelected();

    }
    interface TokenRepository{
        void refreshMinMaxCurrent();
        void test_insertToken(int coef);
        long getMax();
        long getMin();
        long getCurrent();
        long getlastChange();
        void destroy();
    }
    interface WoWTokenServicePresenter{
        boolean init();
        void destroy();
    }
    interface WoWTokenService {
        void makeNotification(long current_price, String region);
    }

    interface TokenServiceRepository{
        long saveWoWTokenAndGetCurrentPrice();
    }

}
