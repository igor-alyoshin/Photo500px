package com.example.photo500px.presenter;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.photo500px.model.Result;

import rx.Observable;

/**
 * Created by igor on 21.12.15.
 */
public interface PhotosPresenter extends SwipeRefreshLayout.OnRefreshListener {
    Observable<Result> loadPhotos(String type, String consumerKey);
    void onDestroyView();
    void onViewCreated();
}
