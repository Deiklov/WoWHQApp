package com.example.wowhqapp.databases.entity;
// package com.example;

import com.squareup.moshi.Json;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class WowClass implements IEntity {
    @PrimaryKey
    @Json(name = "id")
    private int id;
    @NonNull
    @Json(name = "name")
    private String name;

    /**
     * No args constructor for use in serialization
     */
    public WowClass() {
    }

    /**
     * @param id
     * @param name
     */
    @Ignore
    public WowClass(int id, @NonNull String name) {
        super();
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(@NonNull String name) {
        this.name = name;
    }

}
