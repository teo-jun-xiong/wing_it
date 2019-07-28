package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationListView_RecyclerItem implements Parcelable {
    private String mText1, mText2;
    private int mTextHours = 0;

    public LocationListView_RecyclerItem(String text1, String text2){
        mText1 = text1;
        mText2 = text2;
    }

    protected LocationListView_RecyclerItem(Parcel in) {
        mText1 = in.readString();
        mText2 = in.readString();
        mTextHours = in.readInt();
    }

    public static final Creator<LocationListView_RecyclerItem> CREATOR = new Creator<LocationListView_RecyclerItem>() {
        @Override
        public LocationListView_RecyclerItem createFromParcel(Parcel in) {
            return new LocationListView_RecyclerItem(in);
        }

        @Override
        public LocationListView_RecyclerItem[] newArray(int size) {
            return new LocationListView_RecyclerItem[size];
        }
    };

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }

    public void setTextHours(int a) { mTextHours = a; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mText1);
        dest.writeString(mText2);
        dest.writeInt(mTextHours);
    }

    public int getTextHours() {
        return mTextHours;
    }
}
