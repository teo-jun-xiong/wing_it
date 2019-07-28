package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
1. Map: MapFragment with zoom and centering button
2. Search field
3. "Add" button: searches for a location using the text in the search
    field
4. "List" button: proceeds to launch LocationListView.java which
    shows a RecyclerView locationList of the added locations
 */

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private static final int LOCATION_REQUEST = 500;
    private ArrayList<String> locationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);

        // Customise the styling of the base map using a JSON object defined in a raw resource file.
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        map.setMyLocationEnabled(true);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                    break;
                }
        }
    }

    public void onMapAdd(View view) {
        EditText locationSearch = findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location.equals("")) {
            Toast.makeText(getApplicationContext(), "Please input a valid query!", Toast.LENGTH_SHORT).show();

        } else {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            //  storing the longitude and latitude of the searched location, a marker is added to the map,
            // and centers the user's view onto the marker
            map.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));

            // the locationList contains various possible addresses, index 0 is the most relevant result
            locationList.add(address.getAddressLine(0));
            locationSearch.setText("");
        }
    }

    public void onMapList(View view) {
        if (locationList.size() < 3) {
            Toast.makeText(getApplicationContext(), "Please add at least 3 locations!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, LocationListView.class);

            // passes the locationList of locations the user added
            intent.putExtra("name", locationList);
            startActivity(intent);
        }
    }
}