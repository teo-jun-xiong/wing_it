package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class RouteView extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeview);

        Intent incomingIntent = getIntent();
        ArrayList<String> list = incomingIntent.getStringArrayListExtra("name");
        //calculateSSSP(list);
    }

    /*private void calculateSSSP(ArrayList<String> list) {

    }
    */
}
