package com.ciacavus.tapreact;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ciaran on 21/06/2016.
 */
public class Instructions extends AppCompatActivity {

    RelativeLayout instructionsBox;
    TextView instructList;

    //instructions
    public String[] instructions = {"To play the game, you must perform different actions based on the phone.",
            "If you perform an action incorrectly, you lose the game and your score is calculated.",
            "Be careful to be deliberate with your actions as the screen is sensitive.",
            "Your auto-rotate function in your phone needs to be turned on.",
            "In order to gain the best score, you must perform as many actions as possible in a row.",
            "Everyones highscore is shown in the highscores menu option.",
            "Everyones highscore is shown in the highscores menu option.",
            "Everyones highscore is shown in the highscores menu option."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //hide the action bar from this activity
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.instructions_layout);

        instructionsBox = (RelativeLayout) findViewById(R.id.instructionBox);
        instructList = (TextView) findViewById(R.id.list);

    }
}
