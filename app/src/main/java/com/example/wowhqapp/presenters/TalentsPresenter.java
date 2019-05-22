package com.example.wowhqapp.presenters;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.wowhqapp.R;
import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.contracts.TalentsContract;
import com.example.wowhqapp.fragments.talents.TalentsWowClassesFragment;
import com.example.wowhqapp.fragments.talents.TalentsWowSpecsFragment;
import com.example.wowhqapp.fragments.talents.TalentsWowTalentInfoFragment;
import com.example.wowhqapp.fragments.talents.TalentsWowTalentsFragment;
import com.example.wowhqapp.repositories.TalentsRepository;

import java.io.IOException;
import java.util.Random;

public class TalentsPresenter implements TalentsContract.TalentsPresenter {

    MainContract.SettingRepository mSettingRepository;
    TalentsContract.TalentsRepository mTalentsRepository;
    TalentsContract.TalentsView mTalentsView;

    public TalentsPresenter(MainContract.SettingRepository settingRepository, Context talentsView) {
        mSettingRepository = settingRepository;
        mTalentsRepository = new TalentsRepository(talentsView, mSettingRepository);
        mTalentsView = (TalentsContract.TalentsView) talentsView; // TODO
    }

    @Override
    public void loadStage(boolean needAddToBackStack) {
        new TalentsPresenterAsyncLoader().execute(needAddToBackStack);
    }

    public Fragment selectFragment() {
        if (mSettingRepository.getTalentsWowTalentId() != -1) {
            return new TalentsWowTalentInfoFragment();
            // TODO(nickeskov): не забюыть сбросить значение
            //  выбранного таланта в констркуторе фрагмента TalentsWowTalentInfoFragment

        } else if (mSettingRepository.getTalentsWowSpecId() != -1 &&
            mSettingRepository.getTalentsWowSpecOrder() != -1
        ) {
            return new TalentsWowTalentsFragment();

        } else if (mSettingRepository.getTalentsWowClassId() != -1) {
            return new TalentsWowSpecsFragment();
        }

        return new TalentsWowClassesFragment();
    }

    @Override
    public void resetProgress() {
        mSettingRepository.setTalentsWowClassId(-1);
        mSettingRepository.setTalentsWowSpecId(-1);
        mSettingRepository.setTalentsWowSpecOrder(-1);
        mSettingRepository.setTalentsWowTalentId(-1);

        String defaultTitle = mTalentsView.getTalentsViewResources().getString(R.string.talents_a_bar_title_text);
        setTalentsTitle(defaultTitle);
    }

    @Override
    public TalentsContract.TalentsRepository getTalentsRepository() {
        return mTalentsRepository;
    }

    @Override
    public MainContract.SettingRepository getSettingRepository() {
        return mSettingRepository;
    }

    @Override
    public int[] fillColorsTemp() {
        int[] ints = new int[40000];
        Random random = new Random();
        for (int j = 0; j < ints.length; j++) {
            ints[j] = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        }
        return ints;
    }

    class TalentsPresenterAsyncLoader extends AsyncTask<Boolean, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            String oldTalentLang = mSettingRepository.getTalentsLang();
            if (mSettingRepository.getLang() != oldTalentLang) {
                mSettingRepository.setTalentsLang(mSettingRepository.getLang());
                try {
                    mTalentsRepository.refresh();
                } catch (IOException e) {
                    e.printStackTrace();
                    mSettingRepository.setTalentsLang(oldTalentLang);
                }
            }
            return booleans[0];
        }

        @Override
        protected void onPostExecute(Boolean needAddToBackStack) {
            super.onPostExecute(needAddToBackStack);
            mTalentsView.loadFragment(selectFragment(), needAddToBackStack);
        }

    }

    @Override
    public String getTalentsTitle() {
         TextView title = (TextView) mTalentsView.findOnTalentsViewById(R.id.talents_a_bar_title);
        return title.getText().toString();
    }

    @Override
    public void setTalentsTitle(String newTitle) {
        TextView  title = (TextView) mTalentsView.findOnTalentsViewById(R.id.talents_a_bar_title);
        title.setText(newTitle.subSequence(0, newTitle.length()));
    }

}
