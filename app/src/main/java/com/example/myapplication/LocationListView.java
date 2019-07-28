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
This class simply returns a view of the current list of locations the user has input.
It contains 1 button, generate, which will generate the itinerary after crunching the numbers.

There should also be input fields (RecyclerView?) to allow users to input the duration they wish
to stay at each location. Could store an int[], and extract it during the computation of the
algorithm.

It only shows the address now we are unable to show the landmark name, i.e. the address of
Bedok Mall is shown rather than "Bedok Mall" itself.
 */
public class LocationListView extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LocationListView_RecyclerAdapter mAdapter;
    ArrayList<LocationListView_RecyclerItem> listViewItemArrayListRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationlistview);

        Intent incomingIntent = getIntent();
        list = incomingIntent.getStringArrayListExtra("name");
        initRecyclerView(list);
    }

    private void initRecyclerView(ArrayList<String> list) {
        listViewItemArrayListRecycler = new ArrayList<>();
        int len = list.size();

        for (int i = 0; i < len; i++) {
            listViewItemArrayListRecycler.add(new LocationListView_RecyclerItem(list.get(i), list.get(i)));
        }

        mRecyclerView = findViewById(R.id.ListRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new LocationListView_RecyclerAdapter(listViewItemArrayListRecycler);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new LocationListView_RecyclerAdapter.OnItemClickListener() {

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
        listViewItemArrayListRecycler.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void doneItem(int position) {
        EditText recyclerHours = Objects.requireNonNull(mRecyclerView.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.recycler_hours);

        if (recyclerHours.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Please enter an integer!", Toast.LENGTH_SHORT).show();

        } else {
            listViewItemArrayListRecycler.get(position).setTextHours(Integer.parseInt(recyclerHours.getText().toString()));
            System.out.println(position+""+recyclerHours.getText().toString()+""+ listViewItemArrayListRecycler.get(position).getTextHours());
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
            int[] arr_hours = new int[listViewItemArrayListRecycler.size()];

            for (int i = 0; i < arr_hours.length; i++){
                arr_hours[i] = listViewItemArrayListRecycler.get(i).getTextHours();
            }
            System.out.println(arr_hours[0] + " " + arr_hours[1] + " " + arr_hours[2]);

            Intent intent = new Intent(this, ItineraryView.class);
            intent.putExtra("array", listViewItemArrayListRecycler);
            intent.putExtra("days", num_days);
            intent.putExtra("hours", num_hours);
            intent.putExtra("hours_array", arr_hours);
            startActivity(intent);
        }
    }
}
