package com.example.photo500px.view;

import com.example.photo500px.model.Result;

import rx.Subscriber;

/**
 * Created by igor on 21.12.15.
 */
public interface PhotosView {
    Subscriber<Result> getLoadPhotosSubscriber(String type);
}
