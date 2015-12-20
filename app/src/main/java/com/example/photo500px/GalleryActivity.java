package com.example.photo500px;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.photo500px.model.Photo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by igor on 05.12.15.
 */
public class GalleryActivity extends AppCompatActivity {

    private final static String EXTRA_URL_LIST = "EXTRA_URL_LIST";
    private final static String EXTRA_POSITION = "EXTRA_POSITION";

    @Bind(R.id.viewpager_gallery)
    ViewPager viewPager;

    public static void start(Context context, List<Photo> urls, int position) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_URL_LIST, new ArrayList<Parcelable>(urls));
        intent.putExtra(EXTRA_POSITION, position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        ButterKnife.bind(this);
        setupViewPager(viewPager);

        WindowManager.LayoutParams windowManager = getWindow().getAttributes();
        windowManager.dimAmount = 0.75f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public void setupViewPager(ViewPager viewPager) {
        List<Photo> photos = getIntent().getParcelableArrayListExtra(EXTRA_URL_LIST);
        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        GalleryPagerAdapter adapter = new GalleryPagerAdapter(photos);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}
