package com.example.myapplication;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
This class simply returns a view of the current list of locations the user has input.
It contains 1 button, generate, which will generate the itinerary after crunching the numbers.

There should also be input fields (RecyclerView?) to allow users to input the duration they wish
to stay at each location. Could store an int[], and extract it during the computation of the
algorithm.

It only shows the address now we are unable to show the landmark name, i.e. the address of
Bedok Mall is shown rather than "Bedok Mall" itself.
 */
public class ListView extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    ArrayList<RecyclerItem> recyclerItemArrayList;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        Intent incomingIntent = getIntent();
        list = incomingIntent.getStringArrayListExtra("name");
        initRecyclerView(list);
    }

    public void removeItem(int position) {
        recyclerItemArrayList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    // TODO: find a way to remove this, since the exact code is repeated in MapsActivity
    private void initRecyclerView(ArrayList<String> list) {
        recyclerItemArrayList = new ArrayList<>();
        int len = list.size();

        for (int i = 0; i < len; i++) {
            recyclerItemArrayList.add(new RecyclerItem(list.get(i), list.get(i)));
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapter(recyclerItemArrayList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    // opens another activity and passes the ArrayList of locations
    // as well as the number of days and hours for their trip
    public void onGenerate(View view) {
        EditText text_days = findViewById(R.id.text_days);
        int num_days = Integer.parseInt(text_days.getText().toString());
        EditText text_hours = findViewById(R.id.text_hours);
        int num_hours = Integer.parseInt(text_hours.getText().toString());

        Intent intent = new Intent(this, RouteView.class);
        intent.putExtra("array", list);
        intent.putExtra("days", num_days);
        intent.putExtra("hours", num_hours);
        startActivity(intent);
    }
}
