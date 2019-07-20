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

public class RouteView extends AppCompatActivity implements GeoTask.Geo {

    // this class should show the order for users to proceed their days in

    String str_from, str_to; // to store origin and destination
    TextView text_results; // the TextView to display the results
    double[][] adj_mtx; // adjacency matrix to store distance between locations
    double duration_btn_points;
    Queue<Helper_Queue_Pair> q = new LinkedList<>(); // Queue to facilitate storing in array

    ArrayList<String> incoming_List;
    int incoming_days, incoming_hours;
    List<Integer> tour;
    double tourCost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeview);

        text_results = findViewById(R.id.SSSPtext);

        Intent incomingIntent = getIntent();
        incoming_List = incomingIntent.getStringArrayListExtra("name");
        incoming_days = incomingIntent.getIntExtra("days", 0);
        incoming_hours = incomingIntent.getIntExtra("hours", 0);

        processData();
    }

    // this function currently takes the first 2 input locations and adds them to the URL,
    // which is then executed by initialising a new Geotask object with the URL as the parameter
    // TODO: include the entire list and obtain the SSSP algo
    private void processData() {

        adj_mtx = new double[incoming_List.size()][incoming_List.size()];

        // store data in adj_mtx
        for (int i = 0; i < incoming_List.size() - 1; i++)
            for (int j = i + 1; i != j && j < incoming_List.size(); j++) {
                q.add(new Helper_Queue_Pair(i, j));
                System.out.println(i + " " + j + q.peek().get_row() + " " + q.peek().get_col());
                str_from = incoming_List.get(i);
                str_to = incoming_List.get(j);
                String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + str_to + "&mode=driving&language=fr-FR&avoid=tolls&key=AIzaSyAJumGU3xXEGgWzit5j8ncu14grobB5ZYI";
                new GeoTask(RouteView.this).execute(url);
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
            Helper_Queue_Pair temp_pair = q.poll();
            int temp_r = temp_pair.get_row();
            int temp_c = temp_pair.get_col();
            adj_mtx[temp_r][temp_c] = duration_btn_points;
            adj_mtx[temp_c][temp_r] = duration_btn_points;
        }

        // executes TSP when the data is processed
        if (q.isEmpty()) {
            TspDynamicProgrammingIterative solver = new TspDynamicProgrammingIterative(adj_mtx);
            tour = solver.getTour();
            tourCost = solver.getTourCost();
            TextView textView = findViewById(R.id.SSSPtext);
            StringBuilder temp = new StringBuilder();
            temp.append("Tour:");
            temp.append(tour);
            temp.append("\n");
            temp.append(tourCost);
            textView.setText(temp);
        }
    }

    private void printItinerary() {
        TextView textView = findViewById(R.id.SSSPtext);
        StringBuilder toShow = new StringBuilder();

        for (int a = 0; a < adj_mtx.length; a++) {
            for (int b = 0; b < adj_mtx.length; b++) {
                toShow.append(adj_mtx[a][b]).append(" ");
                System.out.print(adj_mtx[a][b] + " ");
            }
            toShow.append("\n");
            System.out.println();
        }
        textView.setText(toShow);
    }
}
