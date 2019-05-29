package com.example.wowhqapp.presenters;

import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.repositories.AuctionsRepo;
import com.example.wowhqapp.repositories.SettingRepository;

public class AuctionsPresenter implements MainContract.AuctionsPresenter {

    private SettingRepository mSettingRepository;
    private Boolean mTypeOfActivity; //False - для аукциона
    private Boolean mIsNetQueryEnable;
    private Boolean mIsRecyclerAdapterEnable;
    private Boolean mIsAllAuctionsDownload;
    private Integer mCurrentPage;
    private MainContract.AuctionsRepo mAuctionsRepo;
    private MainContract.AuctionsView mAuctionsView;
    //private MainContract.AuctionsListFragView mAuctionsListFragView;



    public AuctionsPresenter(MainContract.AuctionsView auctionsView, SettingRepository settingRepository){
        mCurrentPage = 1;
        mAuctionsView = auctionsView;
        mSettingRepository = settingRepository;
        mIsNetQueryEnable = false;
        mIsRecyclerAdapterEnable = false;
        mIsAllAuctionsDownload = false;

    }

    @Override
    public void init(Boolean type, String[] titles) {
        mTypeOfActivity = type;
        mAuctionsView.setTitle(mTypeOfActivity ? titles[0] : titles[1] + " - " + mSettingRepository.getSlug());
        mAuctionsView.setFragment(type);
        mAuctionsRepo = new AuctionsRepo(mSettingRepository, mTypeOfActivity, this);
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
            mAuctionsView.hideProgressBar();
        }

    }

    @Override
    public void notifyLittleData() {
        mAuctionsRepo.deleteAllLots();
        mAuctionsRepo.downloadLots(1);
        if (mIsNetQueryEnable){
            mAuctionsView.hideProgressBar();
        }
    }

    @Override
    public void onDestroy() {
        mAuctionsRepo.destroy();
    }

    @Override
    public void onScrollDown() {
        if (mIsNetQueryEnable){
            mCurrentPage++;
            if(!mIsAllAuctionsDownload){
                mAuctionsView.showProgressBar();
            }
            mAuctionsRepo.downloadLots(mCurrentPage);
        }
    }

    @Override
    public void onScrollDownNotToEnd() {
        mAuctionsView.hideLoadNewDataBtn();
    }

    @Override
    public void onScrollUp() {
        if (!mIsNetQueryEnable){
            mAuctionsView.showLoadNewDataBtn();
        }
    }

    @Override
    public void onLoadNewDataBtnClick() {
        mIsNetQueryEnable = true;
        mAuctionsRepo.deleteAllLots();
        mAuctionsRepo.resetLots();
        mAuctionsView.hideLoadNewDataBtn();
        mAuctionsView.notifyAuctionsChange();
        mAuctionsView.addProgressBar();
        mAuctionsView.showProgressBar();
    }

    @Override
    public void onGetFirstPageError() {
        mAuctionsView.showErrorView();
    }

    @Override
    public void onAllAuctionsDownload() {
        mIsAllAuctionsDownload = true;
        mAuctionsView.hideProgressBar();
    }

    @Override
    public String onGetGlideUrl(String icon_name) {
        return "http://media.blizzard.com/wow/icons/56/" + icon_name + ".jpg";
    }
}
