package com.example.myapplication;

public class RecyclerItem {
    private String mText1, mText2;

    public RecyclerItem(String text1, String text2){
        mText1 = text1;
        mText2 = text2;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }
}
