package com.example.wowhqapp.contracts;

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
    }
    interface SettingPresenter{
        String getSlug();
        String getRegion();
        String getLang();
        void init();
        void setSlug(String value);
        void setRegion(String value);
        void setLang(String value);
        void MenuItemSelected();
    }
    interface SettingView{
        void SetSpinnerValues(String slug, String region, String lang);
        void CloseSetting();
    }
    interface AuctionsPresenter{
        void init(Boolean type, String[] titles);
        void MenuItemSelected();
    }
    interface AuctionsView{
        void closeAuctions();
        void setTitle(String txt);
        void setFragment(Boolean type);
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
