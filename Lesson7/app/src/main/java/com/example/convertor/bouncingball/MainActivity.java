package com.example.convertor.bouncingball;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements View.OnClickListener {
    private BoucingBallview Bb;
    private Button changebutton;
    private Bitmap ball;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bb = new BoucingBallview(this);
        changebutton = new Button(this);
        changebutton.setWidth(200);
        changebutton.setHeight(100);
        changebutton.setBackgroundColor(Color.LTGRAY);
        changebutton.setTextColor(Color.RED);
        changebutton.setTextSize(20);
        changebutton.setText("Changeshape");
        changebutton.setOnClickListener(this);
        changebutton.setGravity(Gravity.CENTER);
        FrameLayout GameLayout = new FrameLayout(this);
        LinearLayout ButtonLayout = new LinearLayout(this);
        ButtonLayout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        ButtonLayout.addView(changebutton);
        GameLayout.addView(Bb);
        GameLayout.addView(ButtonLayout);
        setContentView(GameLayout);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        Bb.killThread(); //Notice this reaches into the GameView object and runs the killThread mehtod.
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Bb.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Bb.changebutton();

    }
}


