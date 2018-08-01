package com.example.s.app_fir;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int MY_REQUEST_INT = 177;
    private long value_pos;
    private String value_ubi;

    public String getValue_ubi() {
        return value_ubi;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //sacamos los datos de MainActivity
        this.value_pos = getIntent().getIntExtra("posicion", 0);
        this.value_ubi = getIntent().getStringExtra("ubicacion");

        Log.d("STATUS", "POSITION " + value_pos);
        Log.d("STATUS", value_ubi);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //ENABLE CURRENT LOCATION

        //aca chequeamos los permisos de localizacion - GPS
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //SI NO TENEMOS PERMISOS SE EJECUTA ESTO SOLICITANDO PERMISOS
            // we nedd to let the user allowing the permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_INT );
            }
            return;
        } else {
            //here the code of granted permission
            mMap.setMyLocationEnabled(true);
        }

        //tipo de mapa se puede cambiar
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //habilitamos opciones de zoom en mapa
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);

        LatLng ubicacion = onMapSearch();

        float zoomlevel = 15;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,zoomlevel));
        mMap.addMarker(new MarkerOptions().position(ubicacion).title(value_ubi));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(ubicacion));
    }

    public LatLng onMapSearch() {

        LatLng latLng = null;

        //mi locacion a buscar esta en la variable value_ubi
        List<Address> addressList = null;

        if ( !value_ubi.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(value_ubi, 1);
                Log.d("STATE", addressList.get(0).toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            latLng = new LatLng(address.getLatitude(), address.getLongitude());

        }

        return latLng;
    }

}
