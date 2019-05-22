package com.example.wowhqapp.databases.entity;

import com.squareup.moshi.Json;

import java.util.List;

public class Auctions {

    @Json(name = "lots")
    private List<Lot> lots = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Auctions() {
    }

    /**
     *
     * @param lots
     */
    public Auctions(List<Lot> lots) {
        super();
        this.lots = lots;
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

}