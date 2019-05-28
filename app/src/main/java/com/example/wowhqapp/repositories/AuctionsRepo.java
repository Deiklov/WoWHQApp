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

import java.net.ConnectException;
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
    private MainContract.SettingRepository mSettingRepository;
    private CompositeDisposable mCompositeDisposable;
    private MainContract.AuctionsPresenter mAuctionsPresenter;
    private Boolean mTypeOfSource; //False - для аукциона
    //Удалены приватные переменные настроек, т.к. если выйти из аукционной активити (но не из приложения), то repo не уничтожится, и когда мы поменяем язык, то он сразуже обновит БД с лотами (что хорошо), но значения настроек для него останутся прежними



    public AuctionsRepo(MainContract.SettingRepository settingRepository ,Boolean is_bestlot_source ,MainContract.AuctionsPresenter auctionsPresenter){
        mAuctionsPresenter = auctionsPresenter;
        mCompositeDisposable = new CompositeDisposable();
        mAuctionsDao = WowhqApplication.getInstance().getAucAndTokenDatabase().getAuctionDao();
        mBestLotsDao = WowhqApplication.getInstance().getAucAndTokenDatabase().getBestLotsDao();
        mSettingRepository = settingRepository;
;
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
    public void resetLots() {
        mListOfLot.clear();
    }

    @Override
    public void downloadLots(final int page) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                Auctions auctions = AucAndTokenNetwork.getInstance().getAuctionsAPI().getAuctionsByPage(mSettingRepository.getSlug(), mSettingRepository.getRegion(), mSettingRepository.getLang(), String.valueOf(page)).execute().body();
                Log.v(WowhqApplication.LOG_TAG, String.valueOf(auctions.getAuctions().size()));
                Thread.sleep(300);
                mAuctionsDao.insertList(auctions.getAuctions());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.v(WowhqApplication.LOG_TAG, "Успешно загружены и сохранены лоты " + mSettingRepository.getSlug() + ".Страница - " + String.valueOf(page));
            }

            @Override
            public void onError(Throwable e) {
                if (e.getClass() == ConnectException.class){
                    Log.v(WowhqApplication.LOG_TAG, "[ОШИБКА] При загр. или сохр. лотов  ConnectException" );
                    if (page == 1) {
                        mAuctionsPresenter.onGetFirstPageError();
                    }
                }
                if (e.getClass() == NullPointerException.class || e.getCause() == null){
                    Log.v(WowhqApplication.LOG_TAG, "[ОШИБКА] При загр. или сохр. лотов  NullPointerException" );
                    if (page >= 2){
                        mAuctionsPresenter.onAllAuctionsDownload();
                    }
                }
                Log.v(WowhqApplication.LOG_TAG, "[ОШИБКА] При загр. или сохр. лотов " + mSettingRepository.getSlug() + ".Страница - " + String.valueOf(page) + "Cause: " + String.valueOf(e.getCause()));
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
    //Какак оказалось, совершенно
    public void destroy() {
        mCompositeDisposable.clear();
    }
}
