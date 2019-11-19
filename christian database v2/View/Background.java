package com.example.ckraft.myapplication.View;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Computer on 4/14/2016.
 */
public class Background {

    private Bitmap image;
    private Bitmap sticky;

    QuickImageView newbg;
    private int x=0,y=0;
    static float xspeed=0, yspeed=0;

    public Background(Bitmap res, QuickImageView a)
    {
        image = res;
        newbg = a;
    }

    public void MakeImage(Bitmap res)
    {
        sticky = res;
    }


    public static void setxspeed(int newvalue)
    {
        xspeed = newvalue;
    }

    public void update(boolean notTouching, double FRICTION)
    {
        if (notTouching && (Math.abs((xspeed))>0 || Math.abs(yspeed)>0) )
        {
            System.out.println("slowing down - Speed is " +xspeed + ", " +yspeed);
            if (xspeed<0)
                xspeed+=FRICTION;
            else
                xspeed -= FRICTION;

            if (yspeed<0)
                yspeed+=FRICTION;
            else
                yspeed -= FRICTION;

            if (Math.abs(xspeed)-FRICTION<0)
                xspeed = 0;
            if (Math.abs(yspeed)-FRICTION<0)
                yspeed = 0;
        }
    }
    public void draw(Canvas canvas)
    {

        x+=xspeed;
        y+=yspeed;
        System.out.println("x:" +x +" y:"+y);
        //this is a white screen
        //canvas.drawBitmap(sticky, 0, 0, null);

        newbg.draw(canvas);

        //this is the quirk
        /*
        canvas.drawBitmap(image, x,y, null);
        if (y>0)
            canvas.drawBitmap(image, x,y-1600, null);
        if (x>0)
            canvas.drawBitmap(image, x-2600,y, null);
        if (x>0 && y>0)
            canvas.drawBitmap(image, x-2600,y-1600, null);
        if (y<-520)
            canvas.drawBitmap(image, x,y+1600, null);
        if (x<-680)
            canvas.drawBitmap(image, x+2600,y, null);
        if (x<0 && y>0)
            canvas.drawBitmap(image, x+2600,y-1600, null);
        if (x>0 && y<-520)
            canvas.drawBitmap(image, x-2600, y+1600,null);
        if (x<-680 && y<-520)
            canvas.drawBitmap(image, x+2600, y+1600,null);
        if (y>1600)
            y=0;
        if (y<-2120)
            y=-520;
        if (x>2600)
            x=0;
        if (x<-3280)
            x=-680;
        */
    }
}
