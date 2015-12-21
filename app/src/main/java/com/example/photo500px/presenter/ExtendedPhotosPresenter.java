package com.example.photo500px.presenter;

import com.example.photo500px.RestService;
import com.example.photo500px.model.Result;
import com.example.photo500px.view.PhotosView;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by igor on 21.12.15.
 */
public class ExtendedPhotosPresenter implements PhotosPresenter {

    private final String type;
    private final String consumerKey;
    private final PhotosView photosView;

    private Subscription photoSubscription;

    public ExtendedPhotosPresenter(PhotosView photosView, String type, String consumerKey) {
        this.photosView = photosView;
        this.type = type;
        this.consumerKey = consumerKey;
    }

    @Override
    public Observable<Result> loadPhotos(String type, String consumerKey) {
        Map<String, String> bundle = new HashMap<>();
        bundle.put("feature", type);
        bundle.put("sort", "created_at");
        bundle.put("image_size", "3");
        bundle.put("include_store", "store_download");
        bundle.put("include_states", "voted");
        bundle.put("consumer_key", consumerKey);
        return RestService.get().getPhotos(bundle)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onDestroyView() {
        if (photoSubscription != null && !photoSubscription.isUnsubscribed())
            photoSubscription.unsubscribe();
    }

    @Override
    public void onViewCreated() {
        if (photoSubscription == null) {
            photoSubscription = loadPhotos(type, consumerKey).subscribe(photosView.getLoadPhotosSubscriber(type));
        }
    }

    @Override
    public void onRefresh() {
        photoSubscription = loadPhotos(type, consumerKey).subscribe(photosView.getLoadPhotosSubscriber(type));
    }
}
