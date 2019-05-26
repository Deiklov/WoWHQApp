package com.example.wowhqapp.databases.entity;

import com.squareup.moshi.Json;

import java.util.List;

public class Auctions {

    @Json(name = "simpleLots")
    private List<SimpleLot> simpleLots = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Auctions() {
    }

    /**
     *
     * @param simpleLots
     */
    public Auctions(List<SimpleLot> simpleLots) {
        super();
        this.simpleLots = simpleLots;
    }

    public List<SimpleLot> getSimpleLots() {
        return simpleLots;
    }

    public void setSimpleLots(List<SimpleLot> simpleLots) {
        this.simpleLots = simpleLots;
    }

}