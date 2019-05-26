package com.example.wowhqapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.presenters.SettingPresenter;
import com.example.wowhqapp.repositories.SettingRepository;

import java.util.Arrays;

public class SettingActivity extends AppCompatActivity implements MainContract.SettingView {

    private SettingPresenter mSettingPresenter;
    private Toolbar mToolbar;
    private Spinner mRegionSpinner;
    private Spinner mSlugSpinner;
    private Spinner mLangSpinner;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mSettingPresenter.onMenuItemSelected();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mSettingPresenter = new SettingPresenter(new SettingRepository(getSharedPreferences(SettingRepository.APP_PREFERENCES, Context.MODE_PRIVATE)), this);

        mRegionSpinner = (Spinner) findViewById(R.id.setting_spin_region);
        mSlugSpinner = (Spinner) findViewById(R.id.setting_spin_server);
        mLangSpinner = (Spinner) findViewById(R.id.setting_spin_lang);
        mToolbar = (Toolbar) findViewById(R.id.setting_toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.setting_a_bar);

        mSettingPresenter.init();

        mRegionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.regions);
                mSettingPresenter.onRegionSelect(choose[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mLangSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.langs);
                mSettingPresenter.onLangSelect(choose[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSlugSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] choose = getResources().getStringArray(R.array.slugs);
                mSettingPresenter.onSlugSelect(choose[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void SetSpinnerValues(String slug, String region, String lang) {

        mRegionSpinner.setSelection(Arrays.asList(getResources().getStringArray(R.array.regions)).indexOf(region));
        mSlugSpinner.setSelection(Arrays.asList(getResources().getStringArray(R.array.slugs)).indexOf(slug));
        mLangSpinner.setSelection(Arrays.asList(getResources().getStringArray(R.array.langs)).indexOf(lang));

    }

    @Override
    public void CloseSetting() {
        this.finish();
    }

}
