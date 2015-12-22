package com.example.photo500px;

import android.app.Application;

import com.example.photo500px.di.AppModule;

import dagger.ObjectGraph;

/**
 * Created by igor on 21.12.15.
 */
public class App extends Application {

    private static App instance = null;

    private ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        graph = ObjectGraph.create(new AppModule());
    }

    public static App get() {
        return instance;
    }

    public void inject(Object object) {
        graph.inject(object);
    }
}
