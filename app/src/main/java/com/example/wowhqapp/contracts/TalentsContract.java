package com.example.wowhqapp.contracts;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;

import com.example.wowhqapp.databases.entity.WowClass;
import com.example.wowhqapp.databases.entity.WowSpec;
import com.example.wowhqapp.databases.entity.WowTalents;

import java.io.IOException;
import java.util.List;

public interface TalentsContract {
    public interface TalentsRepository {
        List<WowClass> getWowClasses();
        List<WowSpec> getWowSpecs(int classId);
        WowTalents getWowTalents(int specId);
        WowTalents getWowTalents();
        void refresh() throws IOException;
    }

    public interface TalentsPresenter {
        void loadStage(boolean needAddToBackStack);
        void resetProgress();
        TalentsRepository getTalentsRepository();
        MainContract.SettingRepository getSettingRepository();
        int[] fillColorsTemp();
    }

    public interface TalentsView {
        void loadFragment(Fragment fragment, boolean needAddToBackStack);
        TalentsPresenter getTalentsPresenter();
    }
}
