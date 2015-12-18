package com.example.convertor.simplegame;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
public class MainActivity extends Activity
{
    private int TIME = 2000;
    private final int[] BACKGROUNDS = new int[]
            {
            R.drawable.back,
            R.drawable.bcg,
            R.drawable.back1,
            R.drawable.back2,
            R.drawable.back3,
            R.drawable.back4
    };
    RelativeLayout mParentLayout;
    private int background = 0;
    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable()
    {
        @Override
        public void run() {
            mParentLayout.setBackgroundResource(BACKGROUNDS[background]);
            background++;
            if (background >= BACKGROUNDS.length)
            {
                background = 0;
            }
            mHandler.postDelayed(mRunnable, TIME);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mParentLayout = (RelativeLayout) findViewById(R.id.parent_layout);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
        mHandler = null;
    }
    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mHandler.post(mRunnable);
    }
    public void play(View v) {
        Intent i = new Intent(this, Game.class);
        startActivity(i);
    }
    public void highscore(View v) {
        Intent i = new Intent(this, Highscore.class);
        startActivity(i);
    }
    public void setting(View v) {
        Intent i = new Intent(this, Setting.class);
        startActivity(i);
    }

    public void exit(View v) {
        System.exit(0);
    }

}