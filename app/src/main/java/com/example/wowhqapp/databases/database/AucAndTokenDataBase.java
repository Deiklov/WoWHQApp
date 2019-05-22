package com.example.wowhqapp.databases.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.wowhqapp.WowhqApplication;
import com.example.wowhqapp.databases.dao.AuctionsDao;
import com.example.wowhqapp.databases.dao.WoWTokenDao;
import com.example.wowhqapp.databases.entity.Lot;
import com.example.wowhqapp.databases.entity.WoWToken;

@Database(entities = {WoWToken.class, Lot.class}, version = 1)
public abstract class AucAndTokenDataBase extends RoomDatabase {

    public abstract WoWTokenDao getWoWTokenDao();
    public abstract AuctionsDao getAuctionDao();

    public static AucAndTokenDataBase getDB(){
        return WowhqApplication.getInstance().getAucAndTokenDatabase();
    }
}
