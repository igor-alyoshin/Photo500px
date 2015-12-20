package com.example.photo500px;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.photo500px.model.Photo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by igor on 05.12.15.
 */
public class GalleryPagerAdapter extends PagerAdapter {

    private final List<Photo> photos;

    public GalleryPagerAdapter(List<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view = LayoutInflater.from(container.getContext()).inflate(R.layout.photo, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
        Picasso.with(container.getContext()).load(photos.get(position).getImageUrl())
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                    }
                });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
