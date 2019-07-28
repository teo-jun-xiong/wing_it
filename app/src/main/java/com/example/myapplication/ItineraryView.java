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
Shows the user the order which they should visit the input locations
Should make use of Travelling Salesperson algorithm, input locations, input days, and hours,
to calculate the shortest path. (minimum cost Hamiltonian path.

The user will then be shown (fragment) the itinerary.
Ideally, it should take into account the number of hours they wish to spend per day and
reset the calculation once that is met.
 */

public class ItineraryView extends AppCompatActivity implements ItineraryView_GeoTask.Geo {

    TextView textView;
    List<Integer> tour;
    double[][] adj_mtx;
    String str_from, str_to;
    double duration_btn_points;
    ArrayList<LocationListView_RecyclerItem> incoming_List;
    int incoming_days, incoming_hours;
    Queue<LocationListView_QueueIntegerPair> q = new LinkedList<>();
    int[] incoming_Hours_Array;
    ItineraryView_Day[] dailyItinerary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itineraryview);
        textView = findViewById(R.id.TSPtext);

        Intent incomingIntent = getIntent();
        incoming_List = incomingIntent.getParcelableArrayListExtra("array");
        incoming_days = incomingIntent.getIntExtra("days", 0);
        incoming_hours = incomingIntent.getIntExtra("hours", 0);
        incoming_Hours_Array = incomingIntent.getIntArrayExtra("hours_array");
        incoming_Hours_Array[0] = 0; // 0 is hotel, so 0 hours
        processData();
    }


    private void generateDailyItinerary(List<Integer> tour) {
        int curr_day = 1;
        boolean exceeded = false;
        int sum = 0;
        dailyItinerary = new ItineraryView_Day[incoming_days + 1];

        for (int j = 0; j < dailyItinerary.length; j++) {
            dailyItinerary[j] = new ItineraryView_Day();
        }

        for (int i = 1; i < tour.size() - 1; i++) {
            sum += incoming_Hours_Array[tour.get(i)];

            if (sum <= incoming_hours) {
                dailyItinerary[curr_day].addItinerary(tour.get(i));

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
            insufficientTime();

        } else {
            showItinerary();
        }

    }

    private void showItinerary() {
        createRecyclerView();
    }

    private void createRecyclerView() {
        initRecyclerView();
    }

    private void insufficientTime() {
        TextView itinerary = findViewById(R.id.TSPtext);
        itinerary.setText("Insufficient time to visit all places of interest!");
    }

    // this function currently takes the first 2 input locations and adds them to the URL,
    // which is then executed by initialising a new Geotask object with the URL as the parameter
    private void processData() {
        adj_mtx = new double[incoming_List.size()][incoming_List.size()];

        // store data in adj_mtx
        for (int i = 0; i < incoming_List.size() - 1; i++)
            for (int j = i + 1; i != j && j < incoming_List.size(); j++) {
                q.add(new LocationListView_QueueIntegerPair(i, j));
                System.out.println(i + " " + j + q.peek().get_row() + " " + q.peek().get_col());
                str_from = incoming_List.get(i).getText1();
                str_to = incoming_List.get(j).getText1();
                String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + str_to + "&mode=driving&language=fr-FR&avoid=tolls&key=AIzaSyAJumGU3xXEGgWzit5j8ncu14grobB5ZYI";
                new ItineraryView_GeoTask(ItineraryView.this).execute(url);
            }
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
            adj_mtx[temp_r][temp_c] = duration_btn_points;
            adj_mtx[temp_c][temp_r] = duration_btn_points;
        }

        // executes TSP when the data is processed
        if (q.isEmpty()) {
            ItineraryView_TSP solver = new ItineraryView_TSP(adj_mtx);
            tour = solver.getTour();
            generateDailyItinerary(tour);
            /* tourCost = solver.getTourCost();
            StringBuilder temp = new StringBuilder();
            temp.append("Tour:");
            temp.append(tour);

            textView.setText(temp);
            */
        }
    }

    private void initRecyclerView() {
        ArrayList<ItineraryView_RecyclerItem> itineraryViewItemArrayListRecycler = new ArrayList<>();
        RecyclerView mRecyclerView;
        ItineraryView_RecyclerAdapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        int len = dailyItinerary.length;

        for (int i = 1; i < len; i++) {
            String temp = "1. ";
            ItineraryView_Day d = dailyItinerary[i];
            temp += incoming_List.get(0).getText1() + "\n";

            int j;
            for (j = 0; j < d.getSize(); j++) {
                temp += (j + 2) + ". " + incoming_List.get(d.getItinerary().get(j)).getText1() + "\n";
            }

            temp += (j + 2) + ". " + incoming_List.get(0).getText1();


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
