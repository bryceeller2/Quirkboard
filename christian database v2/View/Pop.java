package com.example.ckraft.myapplication.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.ckraft.myapplication.R;

public class Pop extends Activity {

    private LocationManager locationManager;
    EditText posttext;
    ImageButton postit;
    Context context;


    static String posttext_var;
    static String Location_var;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

        Intent intent = getIntent();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .7));

        posttext = (EditText) findViewById(R.id.ptext);
        postit = (ImageButton) findViewById(R.id.postbtn);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
            }, 10);
            return;
        } else {
            configureButton();
        }


    }

    public static String getMyString(){
        return posttext_var; }
    public String getMylocation(){
        return Location_var; }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }



    private void configureButton() {

        postit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //GPS a = new GPS("weiners");
                //posttext.setText(a.getLoc(getApplicationContext(), locationManager));

                posttext_var = posttext.getText().toString();
                String text = getMyString();
                //Setup the bundle that will be passed
//                Bundle b = new Bundle();
//                b.putString("Some Key", text);
//                //Setup the Intent that will start the next Activity
//                Intent nextActivity = new Intent(Pop.this, sticky.class);
//                //Assumes this references this instance of Activity A
//                nextActivity.putExtras(b);

                System.out.println("User text: " +text);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("userText", text);
                setResult(Activity.RESULT_OK, resultIntent);

                //startActivity(nextActivity);
                finish();
                //startActivity(new Intent(Pop.this, sticky.class));



            }
        });
    }
}




