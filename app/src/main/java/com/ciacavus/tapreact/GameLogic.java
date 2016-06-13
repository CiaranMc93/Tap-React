package com.ciacavus.tapreact;

import android.content.Context;
import android.content.res.Configuration;
import android.database.SQLException;
import android.hardware.Sensor;
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
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by ciaran on 08/06/2016.
 */
public class GameLogic extends AppCompatActivity implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener{

    public static final int GAME_OVER_FLAG = 50;
    private static int REACTION_TIME = 25;

    //create gesture variable/physical activites
    GestureDetectorCompat mDetector;
    Vibrator vibrate;
    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    //handle the database logic
    DBAdapter db;


    //orientation flags
    private boolean vertical = false;
    private boolean horizontal = false;

    //Textviews/EditTexts/Images
    TextView time;
    TextView status;

    //game logic
    //public String[] gameOptions = {"Double Tap","Single Tap","Swipe Right","Swipe Left", "Shake", "Switch to Horizontal", "Switch to Vertical"};
    public String[] gameOptions = {"Double Tap","Single Tap","Swipe Right","Swipe Left", "Shake"};
    boolean timeUp = false;
    String getCurrentAction = "";
    //start the difficulty to be updated after the successCounter reaches a certain amount
    int difficulty;

    //game timer
    private long startTime = 0L;
    //handle the time
    private Handler customHandler = new Handler();
    //time logic
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    //main timer
    private long overallStartTime = 0L;
    //handle the time
    private Handler overallHandler = new Handler();
    //time logic
    long overallTimeInMilliseconds = 0L;
    long overallTimeSwapBuff = 0L;
    long overallUpdatedTime = 0L;
    int minutes;
    int seconds;

    //define a value to be used for the timing of the moves
    int timerValue;

    //score/stats/player info etc
    int score;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_layout);
        //reset all relative variables when the game is created
        score = 0;
        counter = 0;
        difficulty = 0;
        //start the game timer
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(mainGameTimer, 0);
        //start the overall main timer
        overallStartTime = SystemClock.uptimeMillis();
        overallHandler.postDelayed(overallTimer, 0);

        //database instantiate
        db = new DBAdapter(this);

        //keep the screen on so the person can not be confused if their screen dims
        //sourced from http://stackoverflow.com/questions/4195682/android-disable-screen-timeout-while-app-is-running
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //vibration sourced from http://stackoverflow.com/questions/13950338/how-to-make-an-android-device-vibrate
        vibrate = (Vibrator) GameLogic.this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        vibrate.vibrate(300);

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {

                //shake event
                handleShakeEvent();
            }
        });

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

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    //run the timer
    //runnable timer sourced from http://examples.javacodegeeks.com/android/core/os/handler/android-timer-example/
    private Runnable mainGameTimer = new Runnable()
    {
        public void run()
        {
            //milliseconds calculation
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            //calculate updated time
            updatedTime = timeSwapBuff + timeInMilliseconds;

            //get milliseconds in readable format
            timerValue = (int) updatedTime / 100;

            //pass in the time in milliseconds to be used
            gameOver(timerValue);

            customHandler.postDelayed(this, 0);
        }

    };

    //run the timer
    //runnable timer sourced from http://examples.javacodegeeks.com/android/core/os/handler/android-timer-example/
    private Runnable overallTimer = new Runnable()
    {
        public void run()
        {
            //milliseconds calculation
            overallTimeInMilliseconds = SystemClock.uptimeMillis() - overallStartTime;

            //calculate updated time
            overallUpdatedTime = overallTimeSwapBuff + overallTimeInMilliseconds;

            //seconds is equal to updatedtime in milliseconds * 1000
            int secs = (int) (overallUpdatedTime / 1000);
            //to get mins then seconds * 60 is equal to mins
            int mins = secs / 60;
            secs = secs % 60;

            //update the score each time
            score = (int) overallUpdatedTime / 100;

            //update global variables
            minutes = mins;
            seconds = secs;

            overallHandler.postDelayed(this, 0);
        }

    };

    public void handleShakeEvent()
    {
        String shake = "Shake";
        //update the game status
        updateGameStatus(shake);
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

        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

        String doubleTap = "Double Tap";
        //update the game status
        updateGameStatus(doubleTap);

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

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        String swipe = "";

        //make sure that the x-axis variable has a significant value
        //if a person moves their finger right the x-axis is updated and vice versa for y-axis
        if(velocityX > 100.000)
        {
            swipe = "Swipe Right";
            //update the game status
            updateGameStatus(swipe);
        }
        //make sure that the x-axis variable has a significant value
        else if(velocityX < 100.000)
        {
            swipe = "Swipe Left";
            //update the game status
            updateGameStatus(swipe);
        }

        return true;
    }

    //update the game status
    public void updateGameStatus(String userAction)
    {
        //avoid any logic if the game is over
        if(!timeUp)
        {
            //update a counter as this function is called but not on first time
            //update the difficulty based on the hit counter
            if(counter != 0)
            {
                counter++;
            }
            else if(counter == 10)
            {
                //update the reaction time to make it more difficult
                REACTION_TIME  = REACTION_TIME - 5;
            }
            else if(counter == 20)
            {
                //update the reaction time to make it more difficult
                REACTION_TIME  = REACTION_TIME - 10;
            }
            else if(counter == 30)
            {
                //update the reaction time to make it more difficult
                REACTION_TIME  = REACTION_TIME - 15;
            }

            //if the user action is successful
            if(userAction.contentEquals(getCurrentAction) || userAction.contentEquals("Start"))
            {
                //count the number of times the user was successful
                counter++;
                //respond to user success
                vibrate.vibrate(100);
                //reset the timer to 0 if the hit was successful
                resetRunnableTimer();
                //get a random number between 0 and 3 and create a toast accordingly
                int randomNum = 0 + (int)(Math.random() * gameOptions.length);

//                //check to make sure the orientation request cannot be made when it is in that opientation
//                if(gameOptions[randomNum].contentEquals("Switch to Horizontal") && horizontal == true)
//                {
//
//                }

                //show to the user what they have to do
                status.setText(gameOptions[randomNum]);

                //set the action to the temp variable
                getCurrentAction = gameOptions[randomNum];
            }
            else
            {
                //game over
                gameOver(GAME_OVER_FLAG);
            }
        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig)
//    {
//        super.onConfigurationChanged(newConfig);
//
//        String orientation = "";
//
//        // Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
//        {
//            orientation = "Switch to Horizontal";
//            horizontal = true;
//            //update the game status
//            updateGameStatus(orientation);
//        }
//        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
//        {
//            orientation = "Switch to Vertical";
//            vertical = true;
//            //update the game status
//            updateGameStatus(orientation);
//        }
//
//    }

    //game over logic
    public void gameOver(int milliSeconds)
    {
        //limit the timer in between each move
        if(milliSeconds > REACTION_TIME)
        {
            //set the time up to be true
            timeUp = true;
            //stop calling the timer then call a new one
            customHandler.removeCallbacks(mainGameTimer);

            //set text to game over but replace with logic to update sql database and show user
            time.setText("Game Over");
            status.setText("Final Score: " + score);
            //insert into database
            insertIntoDatabase();
        }
        else
        {
            //print the time
            time.setText("MilliSecs: " + milliSeconds);
        }
    }

    //insert into database the scores each time the game is run
    public boolean insertIntoDatabase()
    {
        //open the database and insert all relevant information into a new row
        //this allows user to keep track of their progress and best times/scores etc
        try{
            db.open();
            //insert new row
            db.insertInfo("Ciaran",score);
            //close the DB
            db.close();
            return true;
        }catch (SQLException e)
        {
            db.close();
            return false;
        }
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
        customHandler.removeCallbacks(mainGameTimer);
        customHandler.postDelayed(mainGameTimer,0);
    }

}
