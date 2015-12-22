package com.example.photo500px.presenter;

import com.example.photo500px.App;
import com.example.photo500px.model.Result;
import com.example.photo500px.model.rest.RestClient;
import com.example.photo500px.view.PhotosView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by igor on 21.12.15.
 */
public class PhotosPresenterImpl implements PhotosPresenter {

    private String type;
    private String consumerKey;
    private PhotosView photosView;

    private Subscription photoSubscription;

    @Inject
    RestClient restClient;

    public PhotosPresenterImpl() {
        App.get().inject(this);
    }

    @Override
    public Observable<Result> loadPhotos() {
        Map<String, String> bundle = new HashMap<>();
        bundle.put("feature", type);
        bundle.put("sort", "created_at");
        bundle.put("image_size", "3");
        bundle.put("include_store", "store_download");
        bundle.put("include_states", "voted");
        bundle.put("consumer_key", consumerKey);
        return restClient.getRestService().getPhotos(bundle)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onStop() {
        if (photoSubscription != null && !photoSubscription.isUnsubscribed()) {
            photoSubscription.unsubscribe();
        }
    }

    @Override
    public void onStart(PhotosView photosView, String type, String consumerKey) {
        this.photosView = photosView;
        this.type = type;
        this.consumerKey = consumerKey;
        if (photoSubscription == null || photoSubscription.isUnsubscribed()) {
            photoSubscription = loadPhotos().subscribe(photosView.getLoadPhotosSubscriber());
        }
    }

    @Override
    public void onRefresh() {
        if (photoSubscription == null || photoSubscription.isUnsubscribed()) {
            photoSubscription = loadPhotos().subscribe(photosView.getLoadPhotosSubscriber());
        }
    }
}
