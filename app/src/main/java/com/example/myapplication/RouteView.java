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
    int[][] adj_mtx;
    int duration_btn_points;
    int row = 0;
    int col = 0;
    int limit;
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeview);

        textView = findViewById(R.id.SSSPtext);

        Intent incomingIntent = getIntent();
        ArrayList<String> incoming_List = incomingIntent.getStringArrayListExtra("name");
        processData(incoming_List, 0);
        // executeSSSP(incoming_List);
    }

    // this function currently takes the first 2 input locations and adds them to the URL,
    // which is then executed by initialising a new Geotask object with the URL as the parameter
    // TODO: include the entire list and obtain the SSSP algo
    private void processData(ArrayList<String> list, int start) {

        adj_mtx = new int[list.size()][list.size()];
        limit = (int) (0.5 * list.size() * (list.size() - 1));

        // store data in adj_mtx
        for (int i = 0; i < list.size() - 1; i++)
            for (int j = i + 1; j < list.size(); j++) {
                str_from = list.get(i);
                str_to = list.get(j);
                String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + str_from + "&destinations=" + str_to + "&mode=driving&language=fr-FR&avoid=tolls&key=AIzaSyAJumGU3xXEGgWzit5j8ncu14grobB5ZYI";
                new GeoTask(RouteView.this).execute(url);
            }

        // execute SSSP
    }

    // temporary: set textview to all the distances
    private void executeSSSP(ArrayList<String> list) {
        StringBuilder to_show = new StringBuilder();

        for (int a = 0; a < list.size(); a++) {
            for (int b = 0; b < list.size(); b++) {
                to_show.append(adj_mtx[a][b]);
            }
            to_show.append("\n");
        }
        System.out.println(adj_mtx[0][1]);

        textView.setText(to_show.toString());
    }

    // this function shows the distance between 2 locations, temporary function to test
    @SuppressLint("SetTextI18n")
    @Override
    public void store_in_adj_mtx(String result) {
        String[] res = result.split(",");
        duration_btn_points = (int) Double.parseDouble(res[0]) / 60;
        System.out.println(duration_btn_points + " " + row + " " + col + " " + count + " " + limit);

        if (col == adj_mtx.length - 1 && row < adj_mtx.length - 1) {      // previously processed data was in last column (and not in the last row)
            col = 0;
            row++;
            adj_mtx[row][col] = duration_btn_points;
            adj_mtx[col][row] = duration_btn_points;
            count++;
            System.out.println("first");
            // previously processed data was in the last row and 2nd last column
            // prints out the matrix

        } else {
            col++;
            count++;
            adj_mtx[row][col] = duration_btn_points;
            adj_mtx[col][row] = duration_btn_points;
            System.out.println("third");
        }

        if (count == limit){
            printMatrix();
        }
    }

    private void printMatrix() {
        TextView textView = findViewById(R.id.SSSPtext);
        StringBuilder toShow = new StringBuilder();

        for (int a = 0; a < adj_mtx.length; a++) {
            for (int b = 0; b < adj_mtx.length; b++) {
                toShow.append(adj_mtx[a][b]).append(" ");
            }
            toShow.append("\n");
        }

        textView.setText(toShow);
    }
}
