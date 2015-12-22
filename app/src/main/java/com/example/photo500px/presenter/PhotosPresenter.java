package com.example.photo500px.presenter;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.photo500px.model.Result;
import com.example.photo500px.view.PhotosView;

import rx.Observable;

/**
 * Created by igor on 21.12.15.
 */
public interface PhotosPresenter extends SwipeRefreshLayout.OnRefreshListener {
    Observable<Result> loadPhotos();
    void onStop();
    void onStart(PhotosView photosView, String type, String consumerKey);
}
