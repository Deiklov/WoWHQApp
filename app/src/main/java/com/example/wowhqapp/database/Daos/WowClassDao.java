package com.example.wowhqapp.database.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.wowhqapp.database.Entities.WowClass;


@Dao
public interface WowClassDao {
    // -------Get--------------------

    @Query("SELECT * FROM wowclass WHERE id=:id")
    WowClass getById(int id);

    //-------Insert------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WowClass... wowClasses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Iterable<WowClass> wowClasses);


    // -------Update-----------------

    @Update
    void update(WowClass... wowClasses);


    // -------Delete-----------------
    @Delete
    void delete(WowClass... wowClasses);

    @Query("DELETE FROM wowclass WHERE id=:id")
    void deleteById(int id);

    @Query("DELETE FROM wowclass")
    void deleteAll();
}
