package com.example.convertor.simpletouchgame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
    private GameView gview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gview = new GameView(this);
        FrameLayout myLayout = new FrameLayout(this);
        myLayout.addView(gview);
        setContentView(myLayout);
    }

    @Override
    protected void onPause() {
        SharedPreferences prefs = getSharedPreferences("ballonData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String tempscore = Integer.toString(gview.getScore());
        editor.putString("ballonScore", tempscore);

        editor.commit();

        super.onPause();
        gview.killThread();
    }

    @Override
    protected void onResume() {
        SharedPreferences prefs = getSharedPreferences("ballonData", MODE_PRIVATE);
        String retrievedHighScore = prefs.getString("ballonScore", "1");

        gview.setScore(Integer.valueOf(retrievedHighScore));

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gview.onDestroy();
    }
}

