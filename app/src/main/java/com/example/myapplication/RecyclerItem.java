package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class RecyclerItem implements Parcelable {
    private String mText1, mText2;
    private int mTextHours = 0;

    public RecyclerItem(String text1, String text2){
        mText1 = text1;
        mText2 = text2;
    }

    protected RecyclerItem(Parcel in) {
        mText1 = in.readString();
        mText2 = in.readString();
        mTextHours = in.readInt();
    }

    public static final Creator<RecyclerItem> CREATOR = new Creator<RecyclerItem>() {
        @Override
        public RecyclerItem createFromParcel(Parcel in) {
            return new RecyclerItem(in);
        }

        @Override
        public RecyclerItem[] newArray(int size) {
            return new RecyclerItem[size];
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
