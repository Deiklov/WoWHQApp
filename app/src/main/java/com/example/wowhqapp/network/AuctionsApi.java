package com.example.wowhqapp.network;

import com.example.wowhqapp.databases.entity.Auctions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuctionsApi {
    @GET("allauc/{slug}/{region}/{lang}/{page}")
    public Call<Auctions> getAutionsByPage(@Path("region") String region,
                                           @Path("slug") String slug,
                                           @Path("page") String page);
}
