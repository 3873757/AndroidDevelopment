package com.example.convertor.bouncingball;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements View.OnClickListener{
    private BoucingBallview Bb;
    private Button changebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View Bb =  new BoucingBallview(this);
        changebutton = new Button(this);
        changebutton.setWidth(350);
        changebutton.setHeight(100);
        changebutton.setBackgroundColor(Color.LTGRAY);
        changebutton.setTextColor(Color.RED);
        changebutton.setTextSize(20);
        changebutton.setText("Change Shape");
        changebutton.setOnClickListener(this);
        changebutton.setGravity(Gravity.CENTER);

        FrameLayout GameLayout = new FrameLayout(this);
        LinearLayout ButtonLayout = new LinearLayout(this);
        ButtonLayout.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        setContentView(GameLayout);
        //setContentView(Bb);

        ButtonLayout.addView(changebutton);
        GameLayout.addView(Bb);
        GameLayout.addView(ButtonLayout);

    }
    @Override
    public void onClick(View v) {
     Bb.onSizeChanged(200,100,200,200);
       // Bb.changeDirection(10,10);

    }
}