package com.quirkboard.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    //CHOCOLATE CHIP MUFFIN

    TextView hello;
    ImageButton homebtn;
    ImageButton postbtn;
    ImageButton settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //This runs first, automatically
        super.onCreate(savedInstanceState);
        //Title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //this would set the view to be the xml
        setContentView(R.layout.activity_main);

        // TUSHARS BUTTONS
        homebtn = (ImageButton) findViewById(R.id.homie);
        settings = (ImageButton) findViewById(R.id.settingbtn);
        hello = (TextView) findViewById(R.id.paul);
        postbtn = (ImageButton) findViewById(R.id.postbtn);

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);

                hello.setText("Hello " + android_id + "!");
                System.out.println("hello world");
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, Statspage.class));
            }
        });


        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, Pop.class));
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
