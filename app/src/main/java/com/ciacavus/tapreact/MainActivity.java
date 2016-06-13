package com.ciacavus.tapreact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //define our buttons to be used
    Button playGame;
    Button highScores;
    Button instructions;
    Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get all the buttons set out in the layout
        playGame = (Button) findViewById(R.id.playGame);
        highScores = (Button) findViewById(R.id.highScores);
        instructions = (Button) findViewById(R.id.settings);
        settings = (Button) findViewById(R.id.settings);

        //set up a button listener
        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the intent of the next application context
                Intent i = new Intent(getApplicationContext(), GameLogic.class);
                //finish the current activity
                finish();
                //start the next activity which is defined in the intent
                startActivity(i);
            }
        });

        //set up a button listener
        highScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the intent of the next application context
                Intent i = new Intent(getApplicationContext(), Highscores.class);
                //finish the current activity
                finish();
                //start the next activity which is defined in the intent
                startActivity(i);
            }
        });

    }
}
