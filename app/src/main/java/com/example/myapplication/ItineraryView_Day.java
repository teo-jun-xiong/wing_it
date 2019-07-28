package com.example.myapplication;

import java.util.ArrayList;

// Helper class to store a day's itinerary and to add to that itinerary

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
