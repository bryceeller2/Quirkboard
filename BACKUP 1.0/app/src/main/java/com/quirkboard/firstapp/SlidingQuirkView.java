package com.quirkboard.firstapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.lang.*;

/**
 * Created by Computer on 4/14/2016.
 */
public class SlidingQuirkView extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    public static final int MOVESPEED = -5;
    private MainThread thread;
    Background bg;
    boolean notTouching=true;
    QuickImageView newbg;
    float startX=0, startY=0;

    double FRICTION = 3;

    public SlidingQuirkView (Context context) {
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // IMAGES ARE CREATED...THREAD IS STARTED **********

        newbg = new QuickImageView(getContext());

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background1), newbg);
        bg.MakeImage(BitmapFactory.decodeResource(getResources(), R.drawable.allwhite));

        //start the constant loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry)
        {
            try {thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {e.printStackTrace();}

            retry = false;
        }
    }

    public void update()
    {
        //Moves the background when the user is not touching the screen
        bg.update(notTouching, FRICTION);
    }

    public boolean onTouchEvent(MotionEvent event)
    {

        System.out.println(getWidth() + ", " +getHeight());
        /*
        System.out.println("getx: " +event.getX());
        System.out.println("getxprecision: " +event.getXPrecision());
        System.out.println("getrawx: " +event.getRawX());
        */


        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            notTouching=false;
            Background.xspeed = 0;
            Background.yspeed = 0;

            startX = event.getX();
            startY = event.getY();
            System.out.println("Screen pressed at (" +startX + ", " +startY +")");



            return true;
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            Background.xspeed = (startX-event.getX())*-3;
            Background.yspeed = (startY-event.getY())*-3;
            startX = event.getX();
            startY = event.getY();

            return true;
        }


        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            System.out.println("finger removed... " +"speed is " +Background.xspeed +"in X and " + Background.yspeed +"in Y");
            System.out.println(Math.abs(Background.xspeed));
            notTouching = true;
            return true;
        }

        return super.onTouchEvent(event);
    }
}
