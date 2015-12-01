package com.example.convertor.convertcuptotblspn;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void add(View v) {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        TextView result = (TextView) findViewById(R.id.result);

        EditText ed1 = (EditText) findViewById(R.id.editText1);

        double a = Double.parseDouble(String.valueOf(ed1.getText()));

        RadioButton TablespntoCup = (RadioButton) findViewById(R.id.TablespntoCup);
        RadioButton CuptoTbleSpoon =(RadioButton) findViewById(R.id.CuptoTbleSpoon);
        RadioButton CuptoTeaSpoon = (RadioButton) findViewById(R.id.CuptoTeaSpoon);

          CuptoTbleSpoon.setChecked(true);

        if (CuptoTbleSpoon.isChecked())
        {
            result.setText(CuptoTbleSpoon(a) + "  Tablespoon");
            CuptoTbleSpoon.setChecked(true);
        } else if(CuptoTeaSpoon.isChecked())
        {
            result.setText(CupstoTeaSpoon(a) + "TeaSpoon");
            CuptoTeaSpoon.setChecked(true);
        }
        else
        {
            result.setText(TablespntoCup(a) + "  Cups");
            TablespntoCup.setChecked(true);
        }

    }

    private double TablespntoCup(double spoon) {
        return spoon / 16;
    }

    private double CuptoTbleSpoon(double cups) {
        return cups * 16;
    }
    private double CupstoTeaSpoon(Double cups){

        return cups*48;
    }
}