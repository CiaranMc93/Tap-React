package com.ciacavus.tapreact;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.junit.rules.Stopwatch;

/**
 * Created by ciaran on 08/06/2016.
 */
public class GameLogic extends AppCompatActivity implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, SensorEventListener {

    //create gesture variable
    GestureDetectorCompat mDetector;

    //Textviews/EditTexts/Images
    TextView time;

    //set up the global variables to manage framerate etc
    private final static int MAX_FPS = 50;
    //num frames to be skipped
    private final static int FRAMES_SKIPPED = 5;
    //declare the game period
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;
    //string to hold the game options and let the user know
    public String[] gameOptions = {"twistIt","tapIt","turnIt"};

    //main timer
    private long startTime = 0L;
    //handle the time
    private Handler customHandler = new Handler();
    //time logic
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int minutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_layout);

        //keep the screen on so the person can not be confused if their screen dims
        //sourced from http://stackoverflow.com/questions/4195682/android-disable-screen-timeout-while-app-is-running
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //start the timer and make play button invisible
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);

        //vibration sourced from http://stackoverflow.com/questions/13950338/how-to-make-an-android-device-vibrate
        Vibrator playMatch = (Vibrator) GameLogic.this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        playMatch.vibrate(300);

        //Textviews/EditTexts/Images
        time = (TextView)findViewById(R.id.time);


        //get a random number between 0 and 3 and create a toast accordingly
        int randomNum = 0 + (int)(Math.random() * 3);

        //show to the user what they have to do
        Toast.makeText(GameLogic.this, gameOptions[randomNum], Toast.LENGTH_LONG).show();

        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this, this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);

    }

    //run the timer
    //runnable timer sourced from http://examples.javacodegeeks.com/android/core/os/handler/android-timer-example/
    private Runnable updateTimerThread = new Runnable()
    {
        public void run()
        {
            //milliseconds calculation
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            //calculate updated time
            updatedTime = timeSwapBuff + timeInMilliseconds;

            //seconds is equal to updatedtime in milliseconds * 1000
            int secs = (int) (updatedTime / 1000);
            //to get mins then seconds * 60 is equal to mins
            int mins = secs / 60;
            secs = secs % 60;

            //update global variables
            minutes = mins;

            customHandler.postDelayed(this, 0);
        }

    };

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        Log.d("E: " , "Single Tap " + e);

        //show to the user what they have done
        Toast.makeText(GameLogic.this, "Single Tap", Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

        Log.d("E: " , "Double Tap " + e);

        //show to the user what they have done
        Toast.makeText(GameLogic.this, "Double Tap", Toast.LENGTH_LONG).show();

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {

        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //show to the user what they have done
        Toast.makeText(GameLogic.this, "Long Pressed", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //make sure that the x-axis variable has a significant value
        //if a person moves their finger right the x-axis is updated and vice versa for y-axis
        if(velocityX > 100.000)
        {
            //show to the user what they have done
            Toast.makeText(GameLogic.this, "Swiped Right", Toast.LENGTH_LONG).show();
        }
        //make sure that the x-axis variable has a significant value
        else if(velocityX < 100.000)
        {
            //show to the user what they have done
            Toast.makeText(GameLogic.this, "Swiped Left", Toast.LENGTH_LONG).show();
        }

        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
