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
        void setSlug(String value);
        String getRegion();
        void setRegion(String value);
        String getLang();
        void setLang(String value);

        String getTalentsLang();
        void setTalentsLang(String value);
        int getTalentsWowClassId();
        void setTalentsWowClassId(int wowClassId);
        int getTalentsWowSpecId();
        void setTalentsWowSpecId(int wowSpecId);
        int getTalentsWowSpecOrder();
        void setTalentsWowSpecOrder(int wowSpecOrder);
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
}
