package com.quirkboard.imagebutton;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView hello;

    ImageButton homebtn;
    ImageButton addbtn;
    ImageButton settings;
    sticky note;
    TextView all_posts[];
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        homebtn = (ImageButton) findViewById(R.id.homie);
        settings = (ImageButton) findViewById(R.id.settingbtn);
        hello = (TextView) findViewById(R.id.paul);

        homebtn.setOnClickListener(new View.OnClickListener(){
            @Override
         public void onClick(View view) {

             hello.setText("The home button works!");

                System.out.println("hello world");

         }
    });
        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, Statspage.class));
            }
        });

    addbtn = (ImageButton) findViewById(R.id.plusbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Pop.class));
            }

        });
}}
