package ru.sfu.collectiverides;
//подключение библиотек
import android.content.Context;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.List;

import ru.sfu.maps.DrawMarker;
import ru.sfu.maps.DrawRouteMaps;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//инициализация точек отправления и окончания
    private GoogleMap mMap;
    private String pointA;
    private String pointB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        pointA = getIntent().getExtras().getString("pointA");
        pointB = getIntent().getExtras().getString("pointB");
    }

//получение данных из строки pointB
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng point = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            point = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return point;
    }
//Создание точек для отображения на карте
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng origin = getLocationFromAddress(MapsActivity.this, "Красноярск " + pointA);
        LatLng destination = getLocationFromAddress(MapsActivity.this, "Красноярск " + pointB);

        DrawRouteMaps.getInstance(this)
                .draw(origin, destination, mMap);

        DrawMarker.getInstance(this).draw(mMap, origin, R.drawable.marker_a, pointA);
        DrawMarker.getInstance(this).draw(mMap, destination, R.drawable.marker_b, pointB);

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .include(destination).build();
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));
    }

}
