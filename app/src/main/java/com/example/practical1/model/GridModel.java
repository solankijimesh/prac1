package com.example.practical1.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.practical1.util.AppConstant;

public class GridModel implements Parcelable {

    private boolean isClickable;
    private AppConstant.Colors color;

    public GridModel() {
    }

    protected GridModel(Parcel in) {
        isClickable = in.readByte() != 0;
    }

    public static final Creator<GridModel> CREATOR = new Creator<GridModel>() {
        @Override
        public GridModel createFromParcel(Parcel in) {
            return new GridModel(in);
        }

        @Override
        public GridModel[] newArray(int size) {
            return new GridModel[size];
        }
    };

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }

    public AppConstant.Colors getColor() {
        return color;
    }

    public void setColor(AppConstant.Colors color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isClickable ? 1 : 0));
    }
}
