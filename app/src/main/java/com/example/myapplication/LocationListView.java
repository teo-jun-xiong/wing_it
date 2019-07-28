package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

/*
1. Input field for days: number of days during a trip
2. Input field for hours: number of hours a user intend to spend per day
3. Generate button: shows the generated itinerary if it is valid
4. Card view of individual location
    4.1. Delete button: remove selected location
    4.2. Text: shows name of selected location
    4.3. Input field for hours: input number of hours intended to be spent at the selected location
    4.4. Done button: registers the input
 */

public class LocationListView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LocationListView_RecyclerAdapter adapter;
    ArrayList<LocationListView_RecyclerItem> recyclerArrayList;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> intentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationlistview);

        Intent incomingIntent = getIntent();
        intentList = incomingIntent.getStringArrayListExtra("name");
        initialiseLocationListView_RecyclerView(intentList);
    }

    private void initialiseLocationListView_RecyclerView(ArrayList<String> list) {
        recyclerArrayList = new ArrayList<>();
        int len = list.size();

        for (int i = 0; i < len; i++) {
            recyclerArrayList.add(new LocationListView_RecyclerItem(list.get(i), list.get(i)));
        }

        recyclerView = findViewById(R.id.ListRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new LocationListView_RecyclerAdapter(recyclerArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new LocationListView_RecyclerAdapter.OnItemClickListener() {

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }

            @Override
            public void onDoneClick(int position) {
                doneItem(position);
            }
        });
    }

    public void removeItem(int position) {
        recyclerArrayList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void doneItem(int position) {
        EditText recyclerHours = Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.recycler_hours);

        if (recyclerHours.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Please enter an integer!", Toast.LENGTH_SHORT).show();

        } else {
            recyclerArrayList.get(position).setTextHours(Integer.parseInt(recyclerHours.getText().toString()));
            System.out.println(position+""+recyclerHours.getText().toString()+""+ recyclerArrayList.get(position).getTextHours());
        }
    }

    public void onGenerate(View view) {
        EditText text_days = findViewById(R.id.text_days);
        EditText text_hours = findViewById(R.id.text_hours);

        if (text_days.getText().toString().equals("") || text_hours.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Please input required fields!", Toast.LENGTH_SHORT).show();

        } else {
            int num_days = Integer.parseInt(text_days.getText().toString());
            int num_hours = Integer.parseInt(text_hours.getText().toString());
            int[] arr_hours = new int[recyclerArrayList.size()];

            for (int i = 0; i < arr_hours.length; i++){
                arr_hours[i] = recyclerArrayList.get(i).getTextHours();
            }
            System.out.println(arr_hours[0] + " " + arr_hours[1] + " " + arr_hours[2]);

            Intent intent = new Intent(this, ItineraryView.class);
            intent.putExtra("array", recyclerArrayList);
            intent.putExtra("days", num_days);
            intent.putExtra("hours", num_hours);
            intent.putExtra("hours_array", arr_hours);
            startActivity(intent);
        }
    }
}
