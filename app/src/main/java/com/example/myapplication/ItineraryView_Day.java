package com.example.myapplication;

import java.lang.reflect.Array;
import java.util.ArrayList;

class ItineraryView_Day {
    private ArrayList<Integer> itinerary = new ArrayList<>();

    public void addItinerary(int n) {
        itinerary.add(n);
    }

    public int getSize() {
        return itinerary.size();
    }

    public ArrayList<Integer> getItinerary() {
        return itinerary;
    }
}
