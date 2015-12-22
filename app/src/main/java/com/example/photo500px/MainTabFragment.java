package com.example.photo500px;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.photo500px.model.Result;
import com.example.photo500px.presenter.PhotosPresenter;
import com.example.photo500px.view.PhotosView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

public class MainTabFragment extends Fragment implements PhotosView {

    private final static String EXTRA_TYPE = "EXTRA_TYPE";

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    PhotosPresenter photosPresenter;

    private PhotoAdapter photoAdapter;

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
        App.get().inject(this);
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
        swipeRefreshLayout.setOnRefreshListener(photosPresenter);
    }

    @Override
    public void onStart() {
        super.onStart();
        photosPresenter.onStart(this, getArguments().getString(EXTRA_TYPE), getString(R.string.consumer_key));
    }

    @Override
    public void onStop() {
        super.onStop();
        photosPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView.setAdapter(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            photosPresenter.onRefresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Subscriber<Result> getLoadPhotosSubscriber() {
        return new Subscriber<Result>() {
            @Override
            public void onStart() {
                swipeRefreshLayout.setRefreshing(true);
            }

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
        };
    }
}