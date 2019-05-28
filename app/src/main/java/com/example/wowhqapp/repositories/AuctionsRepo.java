package com.example.wowhqapp.repositories;

import android.util.Log;

import com.example.wowhqapp.WowhqApplication;
import com.example.wowhqapp.contracts.MainContract;
import com.example.wowhqapp.databases.dao.AuctionsDao;
import com.example.wowhqapp.databases.dao.BestLotsDao;
import com.example.wowhqapp.databases.entity.Auctions;
import com.example.wowhqapp.databases.entity.BestLot;
import com.example.wowhqapp.databases.entity.Lot;
import com.example.wowhqapp.databases.entity.SimpleLot;
import com.example.wowhqapp.network.AucAndTokenNetwork;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AuctionsRepo implements MainContract.AuctionsRepo {

    private AuctionsDao mAuctionsDao;
    private BestLotsDao mBestLotsDao;
    private List<Lot> mListOfLot;
    private String mSlug;
    private String mRegion;
    private String mLang;
    private CompositeDisposable mCompositeDisposable;
    private MainContract.AuctionsPresenter mAuctionsPresenter;
    private Boolean mTypeOfSource; //False - для аукциона



    public AuctionsRepo(String slug, String region, String lang,Boolean is_bestlot_source ,MainContract.AuctionsPresenter auctionsPresenter){
        mAuctionsPresenter = auctionsPresenter;
        mCompositeDisposable = new CompositeDisposable();
        mAuctionsDao = WowhqApplication.getInstance().getAucAndTokenDatabase().getAuctionDao();
        mBestLotsDao = WowhqApplication.getInstance().getAucAndTokenDatabase().getBestLotsDao();
        mSlug = slug;
        mRegion = region;
        mLang = lang;
        mListOfLot = new ArrayList<>();
        mTypeOfSource = is_bestlot_source;

        if (!mTypeOfSource) {
            mCompositeDisposable.add(
                    mAuctionsDao.getAll().observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<List<SimpleLot>>() {
                                @Override
                                public void accept(List<SimpleLot> simpleLots) throws Exception {
                                    if (simpleLots.size() < 10) {
                                        mAuctionsPresenter.notifyLittleData();
                                    } else {
                                        mListOfLot.clear();
                                        for (SimpleLot simpleLot : simpleLots){
                                            mListOfLot.add((Lot) simpleLot);
                                        }
                                        mAuctionsPresenter.notifyUpdatedData();
                                    }
                                }
                            })
            );
        }else {
            mCompositeDisposable.add(
                    mBestLotsDao.getAll().observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<List<BestLot>>() {
                                @Override
                                public void accept(List<BestLot> bestLots) throws Exception {
                                    if (mListOfLot != null){
                                        mListOfLot.clear();
                                    }
                                    for (BestLot simpleLot : bestLots){
                                        mListOfLot.add((Lot) simpleLot);
                                    }
                                    mAuctionsPresenter.notifyUpdatedData();
                                }
                            })
            );
        }
    }

    @Override
    public List<Lot> getLots() {
        return mListOfLot;
    }

    @Override
    public void downloadLots(final int page) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                Auctions auctions = AucAndTokenNetwork.getInstance().getAuctionsAPI().getAuctionsByPage(mSlug, mRegion, mLang, String.valueOf(page)).execute().body();
                Log.v(WowhqApplication.LOG_TAG, String.valueOf(auctions.getAuctions().size()));
                mAuctionsDao.insertList(auctions.getAuctions());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.v(WowhqApplication.LOG_TAG, "Успешно загружены и сохранены лоты " + mSlug + ".Страница - " + String.valueOf(page));
            }

            @Override
            public void onError(Throwable e) {
                Log.v(WowhqApplication.LOG_TAG, "[ОШИБКА] При загр. или сохр. лотов " + mSlug + ".Страница - " + String.valueOf(page) + "Cause: " + String.valueOf(e.getMessage()));
                e.printStackTrace();
            }
        });
    }

    @Override
    public void deleteAllLots() {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mAuctionsDao.deleteAll();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.v(WowhqApplication.LOG_TAG, "[УСПЕХ] Удалены все лоты");
            }

            @Override
            public void onError(Throwable e) {
                Log.v(WowhqApplication.LOG_TAG, "[ОШИБКА] При попытке удалить все лоты" + " Cause: " + String.valueOf(e.getMessage()));
            }
        });
    }

    @Override
    public void destroy() {
        mCompositeDisposable.clear();
    }
}
