package com.example.myapplication;

// Assists in storing row and column to input into the adjacency matrix

class LocationListView_QueueIntegerPair {

    private int r, c;

    LocationListView_QueueIntegerPair(int a, int b) {
        r = a;
        c = b;
    }

    int get_row(){ return r; }
    int get_col(){ return c; }
}
