package com.ciacavus.tapreact;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import org.junit.rules.Stopwatch;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, SensorEventListener {

    //set up a stopwatch to handle the actions and score the user
    Stopwatch timer;

    final int REFRESH_RATE = 100;

    //string to hold the game options and let the user know
    public String[] gameOptions = {"twistIt","tapIt","turnIt"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //start the timer handler
        mHandler = new Handler();

        //get a random number between 0 and 3 and create a toast accordingly
        int randomNum = 0 + (int)(Math.random() * 3);

        //show to the user what they have to do
        Toast.makeText(MainActivity.this, gameOptions[randomNum], Toast.LENGTH_LONG).show();
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mStarted)
            {
                long seconds = (System.currentTimeMillis() - t) / 1000;
                mHandler.postDelayed(mRunnable,1000L);
            }
        }
    };

    private Handler mHandler;
    private boolean mStarted;

    protected void onStart()
    {
        //start
        super.onStart();
        //set to true
        mStarted = true;
        //post delayed runnable
        mHandler.postDelayed(mRunnable,1000L);
    }

    protected void onStop()
    {
        super.onStop();
        //set to false
        mStarted = false;
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
