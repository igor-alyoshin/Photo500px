package com.example.photo500px.rest;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by igor on 05.12.15.
 */
public class RestService {

    private static final String HOST = "https://api.500px.com";

    private static IRestService service;

    public static IRestService get() {
        IRestService localInstance = service;
        if (localInstance == null) {
            synchronized (IRestService.class) {
                localInstance = service;
                if (localInstance == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(HOST)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    service = localInstance = retrofit.create(IRestService.class);
                }
            }
        }
        return localInstance;
    }
}
