package com.example.ckraft.myapplication.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.ckraft.myapplication.R;
import com.example.ckraft.myapplication.View.Background;


/**
 * Created by Computer on 4/28/2016.
 */
public class QuickImageView extends ImageView
{

    private Bitmap image;
    private Bitmap white;
    private int FRAME_RATE = 30;
    private Handler mHandler;

    Canvas canvas;

    double FRICTION = 1;
    double BACKGROUNDSPEED=2;

    int x;
    int y;

    float startX, startY, xspeed, yspeed;
    boolean notTouching;


    public QuickImageView(Context context)
    {
        //THIS CONSTUCTOR IS NOT USED
        super(context);
        setBackgroundResource(R.drawable.background1);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        white = BitmapFactory.decodeResource(getResources(), R.drawable.allwhite);
        mHandler = new Handler();

        System.out.println("BIG FLOPPY DONKEY DICK");

        startX=0;
        startY=0;
        xspeed=0;
        yspeed=0;
        x=0;
        y=0;
        notTouching = true;
    }

    public QuickImageView(Context context, AttributeSet a)
    {
        //THIS CONSTRUCTOR IS USED
        super(context);
        setBackgroundResource(R.drawable.background1);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        white = BitmapFactory.decodeResource(getResources(), R.drawable.allwhite);
        mHandler = new Handler();

        System.out.println("BIG FLOPPY DONKEY DICK");

        startX=0;
        startY=0;
        xspeed=0;
        yspeed=0;
        x=0;
        y=0;
        notTouching = true;
    }



    private Runnable r = new Runnable() {

        @Override
        public void run() {
            //System.out.println("My image is running?");
            invalidate();
        }
    };
    public void setX(int x)
    {
        x = x;
    }

    public void setY(int y)
    {
        y = y;
    }

    public void setXY(int x, int y)
    {
        x = x;
        y = y;
    }

    public void slowDown(double FRICTION)
    {
        if (Math.abs(xspeed)-FRICTION <0)
            xspeed=0;
        if (Math.abs(yspeed)-FRICTION <0)
            yspeed=0;
        if (Math.abs(xspeed) > 0 )
        {
            if (xspeed>0)
                xspeed-=FRICTION;
            else if (xspeed<0)
                xspeed+=FRICTION;
        }
        if (Math.abs(yspeed) > 0 )
        {
            if (yspeed>0)
                yspeed-=FRICTION;
            else if (yspeed<0)
                yspeed+=FRICTION;
        }
    }

    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if (notTouching)
            slowDown(FRICTION);

        x+=xspeed;
        y+=yspeed;
        //System.out.println("Image reports x:" +x +" y:"+y);
        //this is a white screen
        canvas.drawBitmap(white, 0, 0, null);


        //this is the quirk

        canvas.drawBitmap(image, x,y, null);
        if (y>0)
            canvas.drawBitmap(image, x,y-2600, null);
        if (x>0)
            canvas.drawBitmap(image, x-1600,y, null);
        if (x>0 && y>0)
            canvas.drawBitmap(image, x-1600,y-2600, null);
        if (y<-680)
            canvas.drawBitmap(image, x,y+2600, null);
        if (x<-520)
            canvas.drawBitmap(image, x+1600,y, null);
        if (x<0 && y>0)
            canvas.drawBitmap(image, x+1600,y-2600, null);
        if (x>0 && y<-680)
            canvas.drawBitmap(image, x-1600, y+2600,null);
        if (x<-520 && y<-680)
            canvas.drawBitmap(image, x+1600, y+2600,null);
        if (y>2600)
            y=0;
        if (y<-3280)
            y=-680;
        if (x>1600)
            x=0;
        if (x<-2120)
            x = -520;

        mHandler.postDelayed(r, FRAME_RATE);

       // System.out.println("new draw method ran");

    }

    public boolean onTouchEvent(MotionEvent event)
    {

        //System.out.println(getWidth() + ", " + getHeight());
        /*
        System.out.println("getx: " +event.getX());
        System.out.println("getxprecision: " +event.getXPrecision());
        System.out.println("getrawx: " +event.getRawX());
        */


        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            notTouching=false;
            xspeed = 0;
            yspeed = 0;

            startX = event.getX();
            startY = event.getY();
            System.out.println("Screen pressed at (" +startX + ", " +startY +")");



            return true;
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            xspeed = (float)((startX-event.getX())*-BACKGROUNDSPEED);
            yspeed = (float)((startY-event.getY())*-BACKGROUNDSPEED);
            startX = event.getX();
            startY = event.getY();

            return true;
        }


        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            System.out.println("finger removed... " +"speed is " +xspeed +"in X and " + yspeed +"in Y");
            System.out.println(Math.abs(Background.xspeed));
            notTouching = true;
            return true;
        }

        return super.onTouchEvent(event);
    }
}
