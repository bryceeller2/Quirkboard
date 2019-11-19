package com.example.ckraft.myapplication;


import android.Manifest;
import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.View;

/**
 * Created by Computer on 8/22/2016.
 */
public class GPS
{

    private LocationManager locationManager;

    public GPS(String a)
    {

    }

    public void Fun()
    {
        System.out.println("Fun was had");
    }

    public String getLoc(Context context, LocationManager a)
    {
        locationManager = a;
        String Loc="FAIL";
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (statusOfGPS) {
            String locationProvider = LocationManager.NETWORK_PROVIDER;
            if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return ("Fail Check Self Permission");
            }
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            System.out.println(lastKnownLocation);
            //save the location to the database from here and also get the user text from this this activity.
            Loc = "\n Latitude: " + lastKnownLocation.getLatitude() + "\n Longitude: " + lastKnownLocation.getLongitude();
        } else {
            System.out.print("Going to the GPS Screen");
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(intent);
        }
        return Loc;
    }

}
