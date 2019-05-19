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
        void setWoWTokenServiceEnable(Boolean val);
        void setSlug(String value);
        void setRegion(String value);
        void setLang(String value);
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
        void setPrice(long min, long max, long current);
        void setBox(Boolean val);
    }
    interface WoWTokenPresenter{
        void init();
        void test_updateToken(int coef);
        void setServiceStatus(Boolean val);
        void destroy();
    }
    interface TokenRepository{
        void refreshMinMaxCurrent();
        void test_insertToken(int coef);
        long getMax();
        long getMin();
        long getCurrent();
        void destroy();

    }

}
