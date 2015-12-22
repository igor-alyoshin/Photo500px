package com.example.photo500px.model.rest;

import com.example.photo500px.model.Result;

import java.util.Map;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by igor on 05.12.15.
 */
public class RestClient {

    private static final String HOST = "https://api.500px.com";

    private final RestService restService;

    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restService = retrofit.create(RestService.class);
    }

    public RestService getRestService() {
        return restService;
    }

    public interface RestService {
        @Headers({"Host: api.500px.com",
                "X-Target-URI: https://api.500px.com",
                "Connection: Keep-Alive"})
        @GET("/v1/photos")
        Observable<Result> getPhotos(@QueryMap Map<String, String> options);
    }
}
