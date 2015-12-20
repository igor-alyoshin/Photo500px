package com.example.photo500px;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.photo500px.model.Photo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<Photo> photos;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public ProgressBar progressBar;

        public ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.image);
            progressBar = (ProgressBar) v.findViewById(R.id.progress);
        }
    }

    public PhotoAdapter() {
        photos = new ArrayList<>();
    }

    public void setItems(List<Photo> urls) {
        this.photos.clear();
        if (urls != null)
            this.photos.addAll(urls);
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.progressBar.setVisibility(View.VISIBLE);

        int width = holder.itemView.getResources().getDisplayMetrics().widthPixels;
        holder.itemView.setMinimumHeight(width);
        holder.itemView.setMinimumWidth(width);
        Picasso.with(holder.itemView.getContext()).load(photos.get(position).getImageUrl())
                .resize(width, width)
                .centerInside()
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.start(holder.itemView.getContext(), photos, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}