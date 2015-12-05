package com.example.convertor.bouncingball;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BoucingBallview extends SurfaceView
{
    private SurfaceHolder holder;
    private Bitmap ball;
    private Bitmap ball2;
    private GameThread gthread = null;
    private float ballX = -205.0f;
    private float ballY = 100.0f;
    private float ball2X = -50.0f;
    private float ball2Y = -101.0f;
    private boolean ball2Active = false;
    private int score = 0;
    private Paint scorePaint;
    public BoucingBallview(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //this.holder = holder;
                ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
                ball2 = BitmapFactory.decodeResource(getResources(), R.drawable.ball2);
                scorePaint = new Paint();
                scorePaint.setColor(Color.BLACK);
                scorePaint.setTextSize(50.0f);
                makeThread();
                gthread.setRunning(true);
                gthread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }

            public SurfaceHolder getHolder() {
                return null;
            }
        });
    }
    public void makeThread()
    {
        gthread = new GameThread(this);
    }

    public void killThread()
    {
        boolean retry = true;
        gthread.setRunning(false);
        while (retry)
        {
            try {
                gthread.join();
                retry = false;
            } catch (InterruptedException e)
            {
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.GREEN);
        canvas.drawText("Score: " + String.valueOf(score), 10.0f, 50.0f, scorePaint);
        if(ball2Active)
        {
            ball2Y = ball2Y - 5; // flower travels up the screen 5 pixels per redraw
            if ( ball2Y < 325 ) // if the flower goes beyond the bottom of the Maharishi graphic by 25 pixels
            {
                ball2X = -50.0f; // park the flower
                ball2Y = -101.0f; // and
                ball2Active = false; // Turn off flower drawing
            }
            else // otherwise draw the flower in its new position
            {
                canvas.drawBitmap(ball2, ball2X,ball2Y, null);
            }
        }
        ballX = ballX + 2.0f;
        if(ballX > getWidth()) ballX = -205.0f;

        canvas.drawBitmap(ball, ballX, ballY, null);

        if ( ball2X >= ballX && ball2X <= ballX + ball.getWidth()
                && ball2Y <= ballY + ball.getHeight() && ball2Y >= ballY + ball.getHeight() - 25.0f )
        {
            score++;
        }
    }
    public void changebutton(){
        ball2Active = true;
        ball2X = getWidth() / 2.0f - ball2.getWidth() / 2;
        ball2Y = getHeight() - ball2.getHeight() - 25;
    }

    public void onDestroy() {
        ball.recycle();
        ball = null;
        System.gc();
    }

}


    