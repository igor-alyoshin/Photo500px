package com.example.photo500px.model;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.QueryMap;
import rx.Observable;

public interface RestModel {

    @Headers({"Host: api.500px.com",
            "X-Target-URI: https://api.500px.com",
            "Connection: Keep-Alive"})
    @GET("/v1/photos")
    Observable<Result> getPhotos(@QueryMap Map<String, String> options);
}