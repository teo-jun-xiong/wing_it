package com.example.myapplication;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

    private ArrayList<String> list = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        Intent incomingIntent = getIntent();
        list = incomingIntent.getStringArrayListExtra("name");
        showListOfLocation(list);
    }

    // TODO: find a way to remove this, since the exact code is repeated in MapsActivity
    private void showListOfLocation(ArrayList<String> list) {
        TextView text = findViewById(R.id.text_list);
        List<Address> addressList = null;
        String[] to_print = new String[list.size()];
        StringBuilder final_text = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String temp = list.get(i);
            if (temp != null || !temp.equals("")) {
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(temp, 1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // TODO: change this line of code to show the name of the location rather than street address
            to_print[i] = "- " + addressList.get(0).getAddressLine(0) + "\n";
        }

        for (int j = 0; j < list.size(); j++) {

            final_text.append(to_print[j]);
        }
        text.setText(final_text.toString());
    }

    // opens another activity and passes the ArrayList of locations
    // as well as the number of days and hours for their trip
    public void onGenerate(View view) {
        EditText text_days = findViewById(R.id.text_days);
        int num_days  = Integer.parseInt(text_days.getText().toString());
        EditText text_hours = findViewById(R.id.text_hours);
        int num_hours  = Integer.parseInt(text_hours.getText().toString());

        Intent intent = new Intent(this, RouteView.class);
        intent.putExtra("name", list);
        intent.putExtra("days", num_days);
        intent.putExtra("hours", num_hours);
        startActivity(intent);
    }
}
