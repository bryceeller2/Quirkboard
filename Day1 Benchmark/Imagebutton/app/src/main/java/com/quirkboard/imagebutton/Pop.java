package com.quirkboard.imagebutton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;


/**
 * Created by Tushar on 4/22/16.
 */
public class Pop extends Activity {
    private TextView sms_count;
    private TextView mTextView;
    private EditText mEditText;
    private LocationManager locationManager;
    EditText posttext;
    ImageButton postit;
    static String posttext_var;
    static String Location_var;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .7));
        posttext = (EditText) findViewById(R.id.ptext);
        postit = (ImageButton) findViewById(R.id.postbtn1);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
            }, 10);
            return;
        } else {
            configureButton();
        }
        final TextWatcher mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mTextView.setText(String.valueOf(s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }

    ;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }


    public static String getMyString(){
        return posttext_var; }
    public String getMylocation(){
        return Location_var; }



    private void configureButton() {

        postit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (statusOfGPS) {


                   // posttext.setText("Nigga's Location!");
                    String locationProvider = LocationManager.NETWORK_PROVIDER;
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }




                    Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
                    System.out.println(lastKnownLocation);
                    Location_var="\n Latitude: " + lastKnownLocation.getLatitude() + "\n Longitude: " + lastKnownLocation.getLongitude();
                    posttext_var=posttext.getText().toString();
                    startActivity(new Intent(Pop.this, sticky.class));
                    //System.out.println(Location_var);
                    //save the location to the database from here and also get the user text from this this activity.
                    //posttext.append("\n Latitude: " + lastKnownLocation.getLatitude() + "\n Longitude: " + lastKnownLocation.getLongitude());

                    System.out.print("text is " + getMyString());





                } else {


                    System.out.print("Going to the GPS Screen");
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);



                }










            }


        });


    }


}




