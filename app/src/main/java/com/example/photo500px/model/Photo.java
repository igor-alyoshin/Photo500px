package com.example.photo500px.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by igor on 05.12.15.
 */
public class Photo implements Parcelable {

    @SerializedName("image_url")
    private String imageUrl;

    protected Photo(Parcel in) {
        imageUrl = in.readString();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
    }
}
