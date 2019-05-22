package com.example.wowhqapp.repositories;

import com.example.wowhqapp.WowhqApplication;
import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.databases.dao.AuctionsDao;
import com.example.wowhqapp.databases.entity.Lot;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class AuctionsRepo implements MainContract.AuctionsRepo {

    private AuctionsDao mAuctionsDao;
    private List<Lot> mListLot;
    private String mSlug;
    private String mRegion;
    private String mLang;
    private CompositeDisposable mCompositeDisposable;
    private MainContract.AuctionsPresenter mAuctionsPresenter;


    public AuctionsRepo(String slug, String region, String lang, MainContract.AuctionsPresenter auctionsPresenter){
        mAuctionsPresenter = auctionsPresenter;
        mAuctionsDao = WowhqApplication.getInstance().getAucAndTokenDatabase().getAuctionDao();
        mSlug = slug;
        mRegion = region;
        mLang = lang;

        mCompositeDisposable.add(
                mAuctionsDao.getAll().observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Lot>>() {
                            @Override
                            public void accept(List<Lot> lots) throws Exception {
                                mListLot = lots;
                                if (mListLot.size() < 10){
                                    mAuctionsPresenter.notifyLittleData();
                                }
                                else {
                                    mAuctionsPresenter.notifyUpdatedData();
                                }
                            }
                        })
        );
    }

    @Override
    public List<Lot> getLots() {
        return mListLot;
    }

    @Override
    public void downloadLots(int page) {

    }

    @Override
    public void deleteAllLots() {
        mAuctionsDao.deleteAll();
    }

    @Override
    public void destroy() {
        mCompositeDisposable.clear();
    }
}
