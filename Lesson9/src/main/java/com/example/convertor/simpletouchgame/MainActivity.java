package com.example.convertor.simpletouchgame;
import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends Activity{
    private GameView gview;

        @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gview = new GameView(this);
        FrameLayout myLayout = new FrameLayout(this);
        myLayout.addView(gview);
        setContentView(myLayout);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        gview.killThread();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        gview.onDestroy();
    }
}

