package com.ciacavus.tapreact;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by ciaran on 20/06/2016.
 */
public class GameSettings extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    //public variable
    public static String[] colorArray = {"Blue","Red","Yellow","Purple","Green","Orange"};
    public static String[] difficultyArray = {"Easy","Moderate","Skilled","Expert"};
    //seekbar variables
    SeekBar difficulty;
    SeekBar changeColor;
    int difficultyValue;
    int colorValue;
    TextView changeDifficulty;
    TextView changeColorText;


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //hide the action bar from this activity
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.settings_layout);

        //seekbar/textview/default values variable initialise
        difficulty = (SeekBar) findViewById(R.id.difficulty);
        changeDifficulty = (TextView) findViewById(R.id.textDifficulty);
        changeDifficulty.setText("Difficulty Level: " + difficultyArray[0]);

        //change color
        changeColor = (SeekBar) findViewById(R.id.changeColor);
        changeColorText = (TextView) findViewById(R.id.changeColorText);
        changeColorText.setText("Color Scheme: " + colorArray[0]);
        //set them to 0
        difficulty.setProgress(0);
        changeColor.setProgress(0);

        //add the listener to them
        difficulty.setOnSeekBarChangeListener(this);
        changeColor.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        //switch statement to determine which seekbar is pressed.
        switch(seekBar.getId())
        {
            case R.id.difficulty:
                difficultyValue = progress;
                changeDifficulty.setText("Difficulty Level: " + difficultyArray[progress]);
                break;

            case R.id.changeColor:
                colorValue = progress;
                changeColorText.setText("Color scheme: " + colorArray[progress]);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
