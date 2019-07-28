package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class ItineraryView extends AppCompatActivity implements GeoTask.Geo {

    double tourCost;
    TextView textView;
    List<Integer> tour;
    double[][] adj_mtx;
    String str_from, str_to;
    double duration_btn_points;
    ArrayList<ListViewRecyclerItem> incoming_List;
    int incoming_days, incoming_hours;
    Queue<QueueIntegerPair> q = new LinkedList<>();
    int[] incoming_Hours_Array;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeview);
        textView = findViewById(R.id.SSSPtext);

        Intent incomingIntent = getIntent();
        incoming_List = incomingIntent.getParcelableArrayListExtra("array");
        incoming_days = incomingIntent.getIntExtra("days", 0);
        incoming_hours = incomingIntent.getIntExtra("hours", 0);
        incoming_Hours_Array = incomingIntent.getIntArrayExtra("hours_array");
        processData();
    }

    // this function currently takes the first 2 input locations and adds them to the URL,
    // which is then executed by initialising a new Geotask object with the URL as the parameter
    private void processData() {
        adj_mtx = new double[incoming_List.size()][incoming_List.size()];

        // store data in adj_mtx
        for (int i = 0; i < incoming_List.size() - 1; i++)
            for (int j = i + 1; i != j && j < incoming_List.size(); j++) {
                q.add(new QueueIntegerPair(i, j));
                System.out.println(i + " " + j + q.peek().get_row() + " " + q.peek().get_col());
                str_from = incoming_List.get(i).getText1();
                str_to = incoming_List.get(j).getText1();
                String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + str_to +  "&mode=driving&language=fr-FR&avoid=tolls&key=AIzaSyAJumGU3xXEGgWzit5j8ncu14grobB5ZYI";
                new GeoTask(ItineraryView.this).execute(url);
            }
    }

    // this is only called by the Geo interface in GeoTask class
    // this function shows the distance between 2 locations, temporary function to test
    // assumption is that going from A to B is the same cost as going from B to A
    @SuppressLint("SetTextI18n")
    @Override
    public void store_in_adj_mtx(String result) {
        String[] res = result.split(",");
        duration_btn_points = Double.parseDouble(res[0]) / 60;

        if (q.size() != 0) {
            QueueIntegerPair temp_pair = q.poll();
            int temp_r = temp_pair.get_row();
            int temp_c = temp_pair.get_col();
            adj_mtx[temp_r][temp_c] = duration_btn_points;
            adj_mtx[temp_c][temp_r] = duration_btn_points;
        }

        // executes TSP when the data is processed
        if (q.isEmpty()) {
            ItineraryViewTspAlgorithm solver = new ItineraryViewTspAlgorithm(adj_mtx);
            tour = solver.getTour();
            tourCost = solver.getTourCost();
            StringBuilder temp = new StringBuilder();
            temp.append("Tour:");
            temp.append(tour);

            textView.setText(temp);
        }
    }
}
