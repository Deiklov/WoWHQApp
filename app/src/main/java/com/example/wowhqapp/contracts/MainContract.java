package com.example.wowhqapp.contracts;

import com.example.wowhqapp.databases.entity.SimpleLot;

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
        void CloseSetting();
    }

    //Auctions
    interface AuctionsPresenter{
        void init(Boolean type, String[] titles);
        void MenuItemSelected();
        void notifyUpdatedData();
        void notifyLittleData();
        void onScrollDown();
        void onScrollUp();
    }
    interface AuctionsView{
        void closeAuctions();
        void setTitle(String txt);
        void setFragment(Boolean type);
        //Туда и в его коллегу передаем список лотов
        void initAdapter(List<SimpleLot> simpleLotList);
        void notifyAuctionsChange();
    }

    interface AuctionsListFragView{
        void initAdapter(List<SimpleLot> simpleLotList);
        void notifyAuctionsChange();
    }

    interface AuctionsRepo{
        List<SimpleLot> getLots();
        void downloadLots(int page);
        void deleteAllLots();
        void destroy();

    }


    //WoWToken
    interface WoWTokenView{
        void startService();
        void stopService();
        void setPrice(long min, long max, long current, long lastChange, int icon);
        void setBox(Boolean val);
        void setRadioBtn(Boolean val);
        void setTargetPriceEditText(long price);
    }
    interface WoWTokenPresenter{
        void initPrice();
        void initTargetPriceAndStartService();
        void test_updateToken(int coef);
        void setServiceStatus(Boolean val);
        void setTargetPrice(Boolean val, long price);
        void destroy();
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
        void init(boolean is_from_activity);
        void destroy();
    }
    interface WoWTokenServiceView{
        void makeNotification(long current_price, String region);
        void stopService();
    }
    interface TokenServiceRepository{
        long saveWoWTokenAndGetCurrentPrice();
    }

}
