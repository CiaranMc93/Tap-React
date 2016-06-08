package com.ciacavus.tapreact;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import org.junit.rules.Stopwatch;

/**
 * Created by ciaran on 08/06/2016.
 */
public class GameLogic extends AppCompatActivity implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, SensorEventListener {

    //set up the global variables to manage framerate etc
    private final static int MAX_FPS = 50;
    //num frames to be skipped
    private final static int FRAMES_SKIPPED = 5;
    //declare the game period
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;
    //string to hold the game options and let the user know
    public String[] gameOptions = {"twistIt","tapIt","turnIt"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_layout);

        //get a random number between 0 and 3 and create a toast accordingly
        int randomNum = 0 + (int)(Math.random() * 3);

        //show to the user what they have to do
        Toast.makeText(GameLogic.this, gameOptions[randomNum], Toast.LENGTH_LONG).show();
    }

    //run method that starts the game and updates everything in the game
    public void run()
    {

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

        //make sure that the x-axis variable has a significant value
        //if a person moves their finger right the x-axis is updated and vice versa for y-axis
        if(velocityX > 100.000)
        {

        }
        //make sure that the x-axis variable has a significant value
        else if(velocityX < 100.000)
        {

        }
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
