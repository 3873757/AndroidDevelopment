package com.example.jambo.layout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setGravity(Gravity.TOP);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView label1 = new TextView(this);
        TextView label2 = new TextView(this);
        TextView label3 = new TextView(this);

        label1.setTextSize(20);
        label2.setTextSize(20);
        label3.setTextSize(20);
//        label1.setGravity(Gravity.CENTER);
        label1.setTextColor(Color.RED);
        label2.setTextColor(Color.BLUE);
        label3.setTextColor(Color.BLACK);

//        label1.setWidth(100);
//        label1.setHeight(100);

        label1.setText("Title:- this is the title of the page ,and here we can " +
                "add main thing that we think as they are title of the page and " +
                "it might be list of main points that appear in the first section of the page  ");
        label1.setBackgroundColor(Color.GREEN);

//        label2.setWidth(100);
//        label2.setHeight(100);

        label2.setText("Body:- here we can put all main contents ");
        label2.setBackgroundColor(Color.RED);

//        label3.setWidth(100);
//        label3.setHeight(100);
        label3.setText("Coclusion:- here we can put the summary of the page ");
        label3.setBackgroundColor(Color.YELLOW);

        EditText input1= new EditText(this);
        input1.setBackgroundColor(Color.RED);
        input1.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(label1);
        layout.addView(label2);
        layout.addView(label3);

//        layout.addView(input1);
//        layout.addView(label1);

        setContentView(layout);
        LinearLayout.LayoutParams paramsExample = new LinearLayout.LayoutParams(200,200);
        label1.setLayoutParams(paramsExample);
        label2.setLayoutParams(paramsExample);
        label3.setLayoutParams(paramsExample);
    }

    }

