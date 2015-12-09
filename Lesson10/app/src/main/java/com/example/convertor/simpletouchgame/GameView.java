package com.example.convertor.simpletouchgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameView extends SurfaceView {
    private SurfaceHolder holder;
    private Bitmap ballon;
    private Bitmap ballon2;
    private Bitmap ballon1;
    private MediaPlayer mp;
    private Bitmap splash;

    private Paint scorePaint;
    private GmaThread getthread = null;

    private float ballon1X;
    private float ballon1Y;

    private float ballon2X;
    private float ballon2Y;

    private float ballonX;
    private float ballonY;

    private float splashX;
    private float splashY;

    private Random ballonRandomizer;
    private Random ballon1Randomzier;
    private Random ballon2Randomizer;

    private long ballon2Timer;
    private long ballonTimer;
    private long ballon1Timer;
    //private  msprite =6;

    private int bgmusic;
    private SoundPool soundpool;


    private int score = 0;

    public GameView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                ballon = BitmapFactory.decodeResource(getResources(), R.drawable.ballon);
                ballon2 = BitmapFactory.decodeResource(getResources(), R.drawable.ballon2);
                splash = BitmapFactory.decodeResource(getResources(), R.drawable.splash);
                ballon1 = BitmapFactory.decodeResource(getResources(), R.drawable.ballon1);
                //mp = MediaPlayer.create(context, R.raw.bgmusic);


                ballonRandomizer = new Random();
                // splashRandomizer = new Random();
                ballon2Randomizer = new Random();
                moveballon();
                scorePaint = new Paint();
                scorePaint.setTextSize(50.0f);
                scorePaint.setColor(Color.BLACK);
                makeThread();
                getthread.setRunning(true);
                getthread.start();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }

    public void moveballon() {
        ballonX = (float) ballonRandomizer.nextInt(getWidth() - 200);
        ballonY = (float) ballonRandomizer.nextInt(getHeight() - 175);

        ballon2X = (float) ballon2Randomizer.nextInt(getWidth() - 150);
        ballon2Y = (float) ballon2Randomizer.nextInt(getHeight() - 75);

        ballon1X = (float) ballon2Randomizer.nextInt(getWidth() - 100);
        ballon1Y = (float) ballon2Randomizer.nextInt(getHeight() - 50);

        splashX = 20.0f;
        splashY = 100.0f;
        ballonTimer = System.currentTimeMillis();
        ballon2Timer = System.currentTimeMillis();
        ballon1Timer = System.currentTimeMillis();
    }

    public void makeThread() {
        getthread = new GmaThread(this);
    }

    //    void drawme(Canvas canvas) {
//        canvas.drawColor(Color.WHITE);
//        canvas.drawText("Score: " + String.valueOf(score), 10.0f, 50.0f, scorePaint);
//        canvas.drawBitmap(splash, splashX, splashY, null);
//
//    }
    public void killThread() {
        {
            boolean retry = true;
            getthread.setRunning(false);
            while (retry) {
                try {
                    getthread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float touchedX = e.getX();
        float touchedY = e.getY();
        if (touchedX >= ballonX
                && touchedX <= ballonX + 100.0f
                && touchedY >= ballonY
                && touchedY <= ballonY + 75.0f) {
            score += 10;
            //isTouch = true;
            moveballon();
            isTouch = true;
        }
        return true;
    }
    boolean isTouch = false;

    //int isTimer =1000;
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawText("Score: " + String.valueOf(score), 10.0f, 50.0f, scorePaint);
        if (System.currentTimeMillis() > ballonTimer + 500)
            moveballon();
        canvas.drawBitmap(ballon, ballonX, ballonY, null);
        canvas.drawBitmap(ballon2, ballon2X, ballon2Y, null);
        canvas.drawBitmap(ballon1, ballon1X, ballon1Y, null);

        if (isTouch) {
            canvas.drawBitmap(splash, splashX, splashY, null);
            //moveballon();
            if (System.currentTimeMillis() > ballon2Timer + 500) {
                isTouch = false;
            }
        }
    }
    public int getScore() {
        return score;
    }

    public void setScore(int scoreIn) {
        score = scoreIn;
    }
    public void onDestroy() {
        ballon.recycle();
        splash.recycle();
        ballon = null;
        splash = null;
        System.gc();
    }

}