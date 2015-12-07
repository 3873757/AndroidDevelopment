package com.example.jambo.sampleui;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
public class SampleUIActivity extends ActionBarActivity
{
    public final static String EXTRA_MESSAGE = "com.example.jambo.sampleui.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_ui);
    }
    public void sendMessage(View view){
        Intent intent= new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText)findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
