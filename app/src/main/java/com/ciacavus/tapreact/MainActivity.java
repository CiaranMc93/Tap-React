package com.ciacavus.tapreact;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends AppCompatActivity {

    //define our buttons to be used
    Button playGame;
    Button highScores;
    Button instructions;
    Button settings;

    //auto rotate flag
    boolean auto_rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide the action bar from this activity
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        //get all the buttons set out in the layout
        playGame = (Button) findViewById(R.id.playgame);
        highScores = (Button) findViewById(R.id.highscore);
        instructions = (Button) findViewById(R.id.instructions);
        settings = (Button) findViewById(R.id.settings);

        //set up a button listener
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check to see if the auto rotate functionality is on/has been disabled
                if(android.provider.Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION,0) == 1){
                    auto_rotate = true;
                }else
                {
                    auto_rotate = false;
                }

                //check if the auto rotate function has been switched on
                if(auto_rotate)
                {
                    //get the intent of the next application context
                    Intent i = new Intent(getApplicationContext(), GameLogic.class);
                    //start the next activity which is defined in the intent
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Auto Rotation needs to be turned on!",Toast.LENGTH_LONG).show();
                }

            }
        });

        //set up a button listener
        highScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the intent of the next application context
                Intent i = new Intent(getApplicationContext(), Highscores.class);
                //start the next activity which is defined in the intent
                startActivity(i);
            }
        });

        //set up a button listener
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the intent of the next application context
                Intent i = new Intent(getApplicationContext(), GameSettings.class);
                //start the next activity which is defined in the intent
                startActivity(i);
            }
        });

    }
}
