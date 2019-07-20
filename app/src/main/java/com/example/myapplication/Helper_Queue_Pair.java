package com.example.myapplication;

// Helper class to store the row and column pair for Queue

/*
Helper class for storing row and column attributes
Consists of getter methods to return row and column
Used to push a pair of data into the Queue
 */
class Helper_Queue_Pair {

    private int r, c;

    Helper_Queue_Pair(int a, int b) {
        r = a;
        c = b;
    }

    int get_row(){ return r; }
    int get_col(){ return c; }
}
