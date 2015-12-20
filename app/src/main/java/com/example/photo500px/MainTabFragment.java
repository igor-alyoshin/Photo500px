package com.example.photo500px;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.photo500px.model.Result;
import com.example.photo500px.rest.RestService;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainTabFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private final static String EXTRA_TYPE = "EXTRA_TYPE";

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private PhotoAdapter photoAdapter;
    private Subscription photoSubscription;

    public static MainTabFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TYPE, type);
        MainTabFragment fragment = new MainTabFragment();
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        photoAdapter = new PhotoAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedStateInstance) {
        super.onViewCreated(view, savedStateInstance);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(photoAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_color);
        swipeRefreshLayout.setOnRefreshListener(this);

        if (photoSubscription == null) {
            loadPhotos(getArguments().getString(EXTRA_TYPE));
        }
    }

    private void loadPhotos(String type) {
        swipeRefreshLayout.setRefreshing(true);
        photoSubscription = getPhotosObservable(type).subscribe(new Subscriber<Result>() {
            @Override
            public void onCompleted() {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                String errorMessage = e.getMessage();
                if (TextUtils.isEmpty(errorMessage))
                    errorMessage = "Unknown error";
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onNext(Result result) {
                photoAdapter.setItems(result.getPhotos());
                photoAdapter.notifyDataSetChanged();
            }
        });
    }

    private Observable<Result> getPhotosObservable(String type) {
        Map<String, String> bundle = new HashMap<>();
        bundle.put("feature", type);
        bundle.put("sort", "created_at");
        bundle.put("image_size", "3");
        bundle.put("include_store", "store_download");
        bundle.put("include_states", "voted");
        bundle.put("consumer_key", getString(R.string.consumer_key));
        return RestService.get().getPhotos(bundle)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (photoSubscription != null && !photoSubscription.isUnsubscribed())
            photoSubscription.unsubscribe();
        recyclerView.setAdapter(null);
    }

    public void onActionBarClick(Integer id) {
        if (id == R.id.action_refresh) {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        loadPhotos(getArguments().getString(EXTRA_TYPE));
    }


}