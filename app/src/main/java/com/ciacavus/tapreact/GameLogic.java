package com.ciacavus.tapreact;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ciaran on 08/06/2016.
 */
public class GameLogic extends AppCompatActivity implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, SensorEventListener {

    //create gesture variable
    GestureDetectorCompat mDetector;

    //Textviews/EditTexts/Images
    TextView time;
    TextView status;

    //game logic
    public String[] gameOptions = {"Double Tap","Single Tap","Long Press","Swipe Right","Swipe Left"};
    boolean successHit = false;
    boolean failHit = false;
    boolean timeUp = false;
    String getCurrentAction = "";

    //main timer
    private long startTime = 0L;
    //handle the time
    private Handler customHandler = new Handler();
    //time logic
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int minutes;
    int seconds;
    //define a value to be used for the timing of the moves
    int timerValue;

    //score/stats/player info etc
    int score = 0;
    int ovrTime = 0;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_layout);

        //keep the screen on so the person can not be confused if their screen dims
        //sourced from http://stackoverflow.com/questions/4195682/android-disable-screen-timeout-while-app-is-running
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //start the timer
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);

        //vibration sourced from http://stackoverflow.com/questions/13950338/how-to-make-an-android-device-vibrate
        Vibrator playMatch = (Vibrator) GameLogic.this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        playMatch.vibrate(300);

        //Textviews/EditTexts/Images
        time = (TextView)findViewById(R.id.time);
        status = (TextView)findViewById(R.id.gameStatus);

        //call game status function to start
        updateGameStatus("Start");

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

            //get milliseconds in readable format
            timerValue = (int) updatedTime / 100;

            //update the score each time
            score = timerValue;

            //update global variables
            minutes = mins;
            seconds = secs;

            //pass in the time in milliseconds to be used
            timeExceeded(timerValue);

            customHandler.postDelayed(this, 0);
        }

    };

    //if the time has exceeded a set limit return true
    public void timeExceeded(int milliSeconds)
    {
        //limit the timer in between each move
        if(milliSeconds > 20)
        {
            //game is over
            gameOver();
            timeUp = true;
        }

        //print the time
        time.setText("MilliSecs: " + milliSeconds);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        String singleTap = "Single Tap";
        //update the game status
        updateGameStatus(singleTap);
        //reset the timer to 0 if the hit was successful
        resetRunnableTimer();

        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

        String singleTap = "Double Tap";
        //update the game status
        updateGameStatus(singleTap);
        //reset the timer to 0 if the hit was successful
        resetRunnableTimer();

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

        String singleTap = "Long Press";
        //update the game status
        updateGameStatus(singleTap);
        //reset the timer to 0 if the hit was successful
        resetRunnableTimer();

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //make sure that the x-axis variable has a significant value
        //if a person moves their finger right the x-axis is updated and vice versa for y-axis
        if(velocityX > 100.000)
        {
            String singleTap = "Swipe Right";
            //update the game status
            updateGameStatus(singleTap);
            //reset the timer to 0 if the hit was successful
            resetRunnableTimer();
        }
        //make sure that the x-axis variable has a significant value
        else if(velocityX < 100.000)
        {
            String singleTap = "Swipe Left";
            //update the game status
            updateGameStatus(singleTap);
            //reset the timer to 0 if the hit was successful
            resetRunnableTimer();
        }

        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //update the game status
    public void updateGameStatus(String userAction)
    {
        //update a counter as this function is called but not on first time
        if(counter != 0)
        {
            counter++;
        }
        else
        {

        }

        //if the user action is successful
        if(userAction.contentEquals(getCurrentAction))
        {
            counter++;
        }
        else
        {
            //else game over
            gameOver();
        }

        //get a random number between 0 and 3 and create a toast accordingly
        int randomNum = 0 + (int)(Math.random() * 5);

        //show to the user what they have to do
        status.setText(gameOptions[randomNum]);

        //set the action to the temp variable
        getCurrentAction = gameOptions[randomNum];
    }

    public void gameOver()
    {
        //stop calling the timer then call a new one
        customHandler.removeCallbacks(updateTimerThread);

        //set text to game over but replace with logic to update sql database and show user
        time.setText("Game Over");
    }

    public void resetRunnableTimer()
    {
        //reset all the relevant values
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedTime = 0L;
        minutes = 0;
        seconds = 0;
        startTime = 0;
        startTime = SystemClock.uptimeMillis();
        //define a value to be used for the timing of the moves
        timerValue = 0;
        //stop calling the timer then call a new one
        customHandler.removeCallbacks(updateTimerThread);
        customHandler.postDelayed(updateTimerThread,0);
    }

}
