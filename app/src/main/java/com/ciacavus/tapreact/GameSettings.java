package com.ciacavus.tapreact;

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
    public static String[] colorArray = {"blue","red","yellow","purple","green","orange"};
    //seekbar variables
    SeekBar difficulty;
    SeekBar changeColor;
    int difficultyValue;
    int colorValue;
    TextView changeDifficulty;
    TextView changeColorText;


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
        changeColor = (SeekBar) findViewById(R.id.changeColor);
        changeColorText = (TextView) findViewById(R.id.changeColorText);
        changeDifficulty.setText("Difficulty Level: " + 0);
        changeColorText.setText("Color Scheme: " + colorArray[0]);

        difficulty.setOnSeekBarChangeListener(this);
        changeColor.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if(seekBar.getContext() == difficulty.getContext())
        {
            difficultyValue = progress;
            changeDifficulty.setText("Difficulty Level: " + progress);
        }
        else if(seekBar.getContext() == changeColor.getContext())
        {
            colorValue = progress;
            changeColorText.setText("Color Change: " + colorArray[progress]);
        }
        
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
