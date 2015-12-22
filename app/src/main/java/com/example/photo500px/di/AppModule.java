package com.example.photo500px.di;

import com.example.photo500px.MainTabFragment;
import com.example.photo500px.model.rest.RestClient;
import com.example.photo500px.presenter.PhotosPresenter;
import com.example.photo500px.presenter.PhotosPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {
        MainTabFragment.class,
        PhotosPresenterImpl.class
},
        library = true)
public class AppModule {

    @Provides
    PhotosPresenter providesPhotosPresenter() {
        return new PhotosPresenterImpl();
    }

    @Provides
    @Singleton
    RestClient providesRestService() {
        return new RestClient();
    }
}