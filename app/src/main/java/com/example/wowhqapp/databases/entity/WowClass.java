package com.example.wowhqapp.databases.entity;
// package com.example;

import com.squareup.moshi.Json;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class WowClass implements IEntity {

    @NonNull
    @PrimaryKey
    @Json(name = "id")
    private int id;
    @Json(name = "name")
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WowClass() {
    }

    /**
     * 
     * @param id
     * @param name
     */
    @Ignore
    public WowClass(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
