package com.example.myapplication;

public class ItineraryView_RecyclerItem {
    private int day_number;
    private String day;

    public ItineraryView_RecyclerItem(int n, String d){
        day_number = n;
        day = d;
    }

    public int getDay_number() {
        return day_number;
    }

    public String getDay(){
        return day;
    }

}