package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
Sets the first interface of the app to be activity_map
Allows user to search for and add a location to their list
When the user is done adding locations, they can press the list button to
view a list of their locations

The user is then directed to view activity_list
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private static final int LOCATION_REQUEST = 500;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // checks if permission for location is given
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

            // the list contains various possible addresses, index 0 is the most relevant result
            list.add(address.getAddressLine(0));

        }
    }

    public void onMapList(View view) {
        if (list.size() < 3) {
            Toast.makeText(getApplicationContext(), "Please add at least 3 locations!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ListView.class);

            // passes the list of locations the user added
            intent.putExtra("name", list);
            startActivity(intent);
        }
    }
}