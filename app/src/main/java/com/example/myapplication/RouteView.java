package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

/*
This class shows the user the calculated itinerary and returns a view back.
 */

public class RouteView extends AppCompatActivity implements GeoTask.Geo {

    // this class should show the order for users to proceed their days in

    String str_from, str_to;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeview);

        textView = findViewById(R.id.SSSPtext);

        Intent incomingIntent = getIntent();
        ArrayList<String> incoming_List = incomingIntent.getStringArrayListExtra("name");
        calculateSSSP(incoming_List);
    }

    // this function currently takes the first 2 input locations and adds them to the URL,
    // which is then executed by initialising a new Geotask object with the URL as the parameter
    // TODO: include the entire list and obtain the SSSP algo
    private void calculateSSSP(ArrayList<String> list) {
        str_from = list.get(0);
        str_to = list.get(1);
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + str_to + "&mode=driving&language=fr-FR&avoid=tolls&key=AIzaSyAJumGU3xXEGgWzit5j8ncu14grobB5ZYI";
        new GeoTask(RouteView.this).execute(url);

    }


    // this function shows the distance between 2 locations, temporary function to test
    @SuppressLint("SetTextI18n")
    @Override
    public void setDouble(String result) {
        String[] res = result.split(",");
        double min = Double.parseDouble(res[0]) / 60;
        int dist = Integer.parseInt(res[1]) / 1000;
        textView.setText("Duration= " + (int) (min / 60) + " hr " + (int) (min % 60) + " mins\n" + "Distance= " + dist + " kilometers");

    }
}
