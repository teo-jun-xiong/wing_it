package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
1. CardView for number of days: daily itinerary showing the order of visiting locations
2. Text: should there be insufficient time to visit all the locations, notify user
 */

public class ItineraryView extends AppCompatActivity implements ItineraryView_GeoTask.Geo {
    List<Integer> originalTSPSolution;
    double[][] AdjacencyMatrix;
    String url_origin, url_destination;
    double duration_btn_points;
    ArrayList<LocationListView_RecyclerItem> LLV_recyclerList;
    int incoming_days, incoming_hours;
    Queue<LocationListView_QueueIntegerPair> q = new LinkedList<>();
    int[] HoursArray;
    ItineraryView_Day[] IV_DayArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itineraryview);

        Intent incomingIntent = getIntent();
        LLV_recyclerList = incomingIntent.getParcelableArrayListExtra("array");
        incoming_days = incomingIntent.getIntExtra("days", 0);
        incoming_hours = incomingIntent.getIntExtra("hours", 0);
        HoursArray = incomingIntent.getIntArrayExtra("hours_array");
        HoursArray[0] = 0; // 0 is hotel, so 0 hours
        processData();
    }


    private void generateDailyItinerary(List<Integer> tour) {
        int curr_day = 1;
        boolean exceeded = false;
        int sum = 0;
        IV_DayArray = new ItineraryView_Day[incoming_days + 1];

        for (int j = 0; j < IV_DayArray.length; j++) {
            IV_DayArray[j] = new ItineraryView_Day();
        }

        for (int i = 1; i < tour.size() - 1; i++) {
            sum += HoursArray[tour.get(i)];

            if (sum <= incoming_hours) {
                IV_DayArray[curr_day].addItinerary(tour.get(i));

            } else {
                curr_day++;
                sum = 0;
                i--;

                if (curr_day > incoming_days) {
                    exceeded = true;
                    break;
                }
            }
        }

        if (exceeded) {
            TextView itinerary = findViewById(R.id.TSPtext);
            itinerary.setText("Insufficient time to visit all places of interest!");

        } else {
            initRecyclerView();
        }
    }

    // this function currently takes the first 2 input locations and adds them to the URL,
    // which is then executed by initialising a new Geotask object with the URL as the parameter
    private void processData() {
        AdjacencyMatrix = new double[LLV_recyclerList.size()][LLV_recyclerList.size()];

        // store data in AdjacencyMatrix
        for (int i = 0; i < LLV_recyclerList.size() - 1; i++)
            for (int j = i + 1; i != j && j < LLV_recyclerList.size(); j++) {
                q.add(new LocationListView_QueueIntegerPair(i, j));
                System.out.println(i + " " + j + q.peek().get_row() + " " + q.peek().get_col());
                url_origin = LLV_recyclerList.get(i).getText2();
                url_destination = LLV_recyclerList.get(j).getText2();
                String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + url_origin + "&destinations=" + url_destination + "&mode=driving&language=fr-FR&avoid=tolls&key=AIzaSyAJumGU3xXEGgWzit5j8ncu14grobB5ZYI";
                new ItineraryView_GeoTask(ItineraryView.this).execute(url);
            }
        System.out.println("done");
    }

    // this is only called by the Geo interface in ItineraryView_GeoTask class
    // this function shows the distance between 2 locations, temporary function to test
    // assumption is that going from A to B is the same cost as going from B to A
    @SuppressLint("SetTextI18n")
    @Override
    public void store_in_adj_mtx(String result) {
        String[] res = result.split(",");
        duration_btn_points = Double.parseDouble(res[0]) / 60;

        if (q.size() != 0) {
            LocationListView_QueueIntegerPair temp_pair = q.poll();
            int temp_r = temp_pair.get_row();
            int temp_c = temp_pair.get_col();
            AdjacencyMatrix[temp_r][temp_c] = duration_btn_points;
            AdjacencyMatrix[temp_c][temp_r] = duration_btn_points;
        }

        // executes TSP when the data is processed
        if (q.isEmpty()) {
            ItineraryView_TSP solver = new ItineraryView_TSP(AdjacencyMatrix);
            originalTSPSolution = solver.getTour();
            generateDailyItinerary(originalTSPSolution);
            /* tourCost = solver.getTourCost();
            StringBuilder temp = new StringBuilder();
            temp.append("Tour:");
            temp.append(originalTSPSolution);

            ItineraryView_TextView.setText(temp);
            */
        }
    }

    private void initRecyclerView() {
        ArrayList<ItineraryView_RecyclerItem> itineraryViewItemArrayListRecycler = new ArrayList<>();
        RecyclerView mRecyclerView;
        ItineraryView_RecyclerAdapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        int len = IV_DayArray.length;

        for (int i = 1; i < len; i++) {
            String temp = "1. ";
            ItineraryView_Day d = IV_DayArray[i];
            temp += LLV_recyclerList.get(0).getText1() + " (" + LLV_recyclerList.get(0).getText2() + ")\n";

            int j;
            for (j = 0; j < d.getSize(); j++) {
                temp += (j + 2) + ". " + LLV_recyclerList.get(d.getItinerary().get(j)).getText1() + " (" + LLV_recyclerList.get(d.getItinerary().get(j)).getText2() + ")\n";
            }

            temp += (j + 2) + ". " + LLV_recyclerList.get(0).getText1() + " (" + LLV_recyclerList.get(0).getText2() + ")";


            itineraryViewItemArrayListRecycler.add(new ItineraryView_RecyclerItem(i, temp));
        }

        mRecyclerView = findViewById(R.id.ItineraryRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ItineraryView_RecyclerAdapter(itineraryViewItemArrayListRecycler);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
