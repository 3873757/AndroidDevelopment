package com.example.android.jetboy;
import com.example.android.jetboy.JetBoyView.JetBoyThread;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
public class JetBoy extends Activity implements View.OnClickListener {
    private JetBoyThread mJetBoyThread;
    private JetBoyView mJetBoyView;
    private Button mButton;
    private Button mButtonRetry;
    public void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mJetBoyView = (JetBoyView)findViewById(R.id.JetBoyView);
        mJetBoyThread = mJetBoyView.getThread();
        mButton = (Button)findViewById(R.id.Button01);
        mButton.setOnClickListener(this);
        mButtonRetry = (Button)findViewById(R.id.Button02);
        mButtonRetry.setOnClickListener(this);
       // mJetBoyView.SetButtonView(mButtonRetry);
    }
       public void onClick(View v)
       {
        if (mJetBoyThread.getGameState() == JetBoyThread.STATE_START)
        {
            mButton.setText("PLAY!");
            mJetBoyThread.setGameState(JetBoyThread.STATE_PLAY);
        }
        else if (mJetBoyThread.getGameState() == JetBoyThread.STATE_PLAY)
        {
            mButton.setVisibility(View.INVISIBLE);
            mJetBoyThread.setGameState(JetBoyThread.STATE_RUNNING);
        }
        else if (mButtonRetry.equals(v))
        {
            mButton.setText("PLAY!");
            mButtonRetry.setVisibility(View.INVISIBLE);
            mButton.setText("PLAY!");
            mButton.setVisibility(View.VISIBLE);
            mJetBoyThread.setGameState(JetBoyThread.STATE_PLAY);
        } else
        {
            Log.d("JB VIEW", "unknown click " + v.getId());
            Log.d("JB VIEW", "state is  " + mJetBoyThread.mState);
        }
    }
}
