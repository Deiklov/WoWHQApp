package com.example.wowhqapp.databases.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WoWToken {
    @PrimaryKey
    public long id;
    public long maxPrice;
    public long minPrice;
    public long currentPrice;
    public String region;

}
