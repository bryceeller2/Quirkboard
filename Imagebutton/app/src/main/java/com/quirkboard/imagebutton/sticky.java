package com.quirkboard.imagebutton;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Tushar on 5/16/16.
 */
public class sticky extends MainActivity{



    private String posttext;
    private String Location;
    private int tacks;
    private int de_tacks;
    private int stickiness;

    public sticky(){
        this.posttext = "hell---";
        this.Location = "hell---";
        this.tacks = 0;
        this.de_tacks = 0;
        this.stickiness = 0;
    }

    public sticky(String posttext, String location, int tacks, int detacks, int stickiness) {
        this.posttext = posttext;
        this.Location = location;
        this.tacks = tacks;
        this.de_tacks = detacks;
        this.stickiness = stickiness;
    }


    public String getPosttext() {
        return posttext;
    }

    public String setPosttext(String posttext) {
        this.posttext = posttext;
        return posttext;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getTacks() {
        return tacks;
    }

    public void setTacks(int tacks) {
        this.tacks = tacks;
    }

    public int getDetacks() {
        return de_tacks;
    }

    public void setDetacks(int detacks) {
        this.de_tacks = detacks;
    }

    public int getStickiness() {
        return stickiness;
    }

    public void setStickiness(int stickiness) {
        this.stickiness = stickiness;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.print("hello I entered the onCreate!");
        sticky number;
        number = new sticky(Pop.getMyString(), getLocation(), 0,5,4);

        super.onCreate(savedInstanceState);

        ImageView StickyImage = new ImageView(this);
        TextView posttext = new TextView(this);
        RelativeLayout myLayout= (RelativeLayout) findViewById(R.id.mainboard);

        System.out.print("text is " + getPosttext());
        myLayout.addView(StickyImage);
        myLayout.addView(posttext);
        posttext.setText(number.getPosttext());
        //posttext.setPadding(200,400,0,0);
        posttext.setX(300);
        posttext.setY(800);

        StickyImage.setBackgroundResource(R.drawable.new_s);
        StickyImage.setX(250);
        StickyImage.setY(500);

    }
}
