package com.example.wowhqapp.databases.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.wowhqapp.databases.entity.Lot;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface AuctionsDao {
    @Query("SELECT * FROM lot ")
    Flowable<List<Lot>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Lot lot);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<Lot> lots);

    @Update
    void update(Lot lot);

    @Delete
    void delete(Lot lot);

    @Query("DELETE FROM lot")
    void deleteAll();
}
