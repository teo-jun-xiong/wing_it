package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class ItineraryViewRecyclerItem implements Parcelable {
    private String day_number, mText2;
    private int mTextHours = 0;

    public ItineraryViewRecyclerItem(String text1, String text2){
        day_number = text1;
        mText2 = text2;
    }

    protected ItineraryViewRecyclerItem(Parcel in) {
        day_number = in.readString();
        mText2 = in.readString();
        mTextHours = in.readInt();
    }

    public static final Creator<ItineraryViewRecyclerItem> CREATOR = new Creator<ItineraryViewRecyclerItem>() {
        @Override
        public ItineraryViewRecyclerItem createFromParcel(Parcel in) {
            return new ItineraryViewRecyclerItem(in);
        }

        @Override
        public ItineraryViewRecyclerItem[] newArray(int size) {
            return new ItineraryViewRecyclerItem[size];
        }
    };

    public String getText1() {
        return day_number;
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
        dest.writeString(day_number);
        dest.writeString(mText2);
        dest.writeInt(mTextHours);
    }

    public int getTextHours() {
        return mTextHours;
    }
}
