package com.quirkboard.firstapp;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Computer on 4/14/2016.
 */
public class MainThread extends Thread
{
    private int FPS =30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private SlidingQuirkView SlidingQuirkView;
    private QuickImageView QuickImageView;
    private boolean running;
    private static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, SlidingQuirkView SlidingQuirkView)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.SlidingQuirkView = SlidingQuirkView;

    }

    public MainThread(QuickImageView QuickImageView)
    {
        super();
        this.QuickImageView = QuickImageView;

    }

    @Override
    public void run()
    {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime=0;
        int frameCount=0;
        long targetTime = 1000/FPS;

        while(running) //This function should update every like millisecond or something
        {
            startTime=System.nanoTime();

            System.out.println("Hello3" + canvas);

            canvas = null;
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    //This function draws the quirkboard to the screen
                    QuickImageView.draw(canvas);

                    //this.SlidingQuirkView.draw(canvas);
                    System.out.println("Hello3" + canvas);
                }

            }
            catch (Exception e){}

            finally
            {
                if (canvas!=null)
                {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch(Exception e){e.printStackTrace();}
                }
            }

            //QuickImageView.fakeDraw();

            timeMillis=(System.nanoTime()-startTime)/1000000;
            waitTime=targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}


            totalTime+=System.nanoTime()-startTime;
            frameCount++;
            if (frameCount == FPS)
            {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount=0;
                totalTime=0;
                System.out.println("Average FPS is: "+averageFPS);
            }
        }
    }

    public void setRunning(boolean a)
    {
        running = a;
    }

}
