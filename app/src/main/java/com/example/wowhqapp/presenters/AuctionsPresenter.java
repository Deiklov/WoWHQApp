package com.example.wowhqapp.presenters;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.repositories.AuctionsRepo;
import com.example.wowhqapp.repositories.SettingRepository;

public class AuctionsPresenter implements MainContract.AuctionsPresenter {

    private SettingRepository mSettingRepository;
    private Boolean mTypeOfActivity; //False - для аукциона
    private Boolean mIsNetQueryEnable;
    private Boolean mIsRecyclerAdapterEnable;
    private Integer mCurrentPage;
    private MainContract.AuctionsRepo mAuctionsRepo;
    private MainContract.AuctionsView mAuctionsView;
    //private MainContract.AuctionsListFragView mAuctionsListFragView;



    public AuctionsPresenter(MainContract.AuctionsView auctionsView, SettingRepository settingRepository){
        mCurrentPage = 0;
        mAuctionsView = auctionsView;
        mSettingRepository = settingRepository;
        mIsNetQueryEnable = false;
        mIsRecyclerAdapterEnable = false;

    }

    @Override
    public void init(Boolean type, String[] titles) {
        mTypeOfActivity = type;
        mAuctionsView.setTitle(mTypeOfActivity ? titles[0] : titles[1] + " - " + mSettingRepository.getSlug());
        mAuctionsView.setFragment(type);
        mAuctionsRepo = new AuctionsRepo(mSettingRepository.getSlug(), mSettingRepository.getRegion(), mSettingRepository.getLang(), mTypeOfActivity, this);
    }

    @Override
    public void MenuItemSelected() {
        mAuctionsView.closeAuctions();
    }

    @Override
    public void notifyUpdatedData() {
        if (!mIsRecyclerAdapterEnable){
            mAuctionsView.initAdapter(mAuctionsRepo.getLots());
            mIsRecyclerAdapterEnable = true;
        }
        else {
            mAuctionsView.notifyAuctionsChange();
        }

    }

    @Override
    public void notifyLittleData() {
        mAuctionsRepo.deleteAllLots();
        mAuctionsRepo.downloadLots(1);
    }

    @Override
    public void onScrollDown() {
        if (mIsNetQueryEnable){
            mAuctionsRepo.downloadLots(mCurrentPage);
        }
    }

    @Override
    public void onScrollUp() {
        if (!mIsNetQueryEnable){

        }
    }
}
