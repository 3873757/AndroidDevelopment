package com.example.convertor.simpletouchgame;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
public class GmaThread extends Thread {
    private GameView view;
    private boolean running = false;

    public GmaThread(GameView viewIn) {

        this.view = viewIn;
    }
    public void setRunning(boolean run) {

        running = run;
    }
    @SuppressLint("WrongCall")
    @Override
    public void run() {
        while (running) {
            Canvas c = null;
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                    //mp.start();
                      //view.drawme(c);//if the brace of constructor on main acctivity class is not well done it shows error
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
        }
    }
}

