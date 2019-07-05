package com.example.myapplication;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private void showListOfLocation(ArrayList<String> list) {
        TextView text = findViewById(R.id.textView2);
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

            to_print[i] ="- " + addressList.get(0).getAddressLine(0) + "\n";
        }

        for (int j = 0; j < list.size(); j++){

            final_text.append(to_print[j]);
        }
        text.setText(final_text.toString());
    }

    public void onGenerate(View view) {
        Intent intent = new Intent(this, RouteView.class);
        intent.putExtra("name", list);
        startActivity(intent);
    }
}
