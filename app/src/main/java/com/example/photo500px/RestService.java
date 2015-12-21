package com.example.photo500px;

import com.example.photo500px.model.RestModel;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by igor on 05.12.15.
 */
public class RestService {

    private static final String HOST = "https://api.500px.com";

    private static RestModel service;

    public static RestModel get() {
        RestModel localInstance = service;
        if (localInstance == null) {
            synchronized (RestModel.class) {
                localInstance = service;
                if (localInstance == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(HOST)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    service = localInstance = retrofit.create(RestModel.class);
                }
            }
        }
        return localInstance;
    }
}
