package com.ciacavus.tapreact;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by ciaran on 13/06/2016.
 */
public class Highscores extends AppCompatActivity {

    //create new DB variable
    DBAdapter db;

    //animation
    Animate animate;

    //get the layout of the screen
    LinearLayout li;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //hide the action bar from this activity
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.highscore_layout);

        //instantiate DBAdapter
        db = new DBAdapter(this);

        //animate
        animate = new Animate(this);

        //get the layout
        li = (LinearLayout)findViewById(R.id.highscore);

        try{
            db.open();
            //create an SQL cursor for the functionality of getting all the contacts
            Cursor c = db.getAllInfo("Highscores");

            int count = 0;

            //perform a query and in a do while loop,
            //run over each search result of the query
            if(c.moveToFirst())
            {
                do{
                    displayHighscores(c,count);
                    count++;
                }
                while(c.moveToNext());


            }

            db.close();
        }catch (SQLException e)
        {
            //set the layout params of the text view
            ActionBar.LayoutParams lParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lParams.setMargins(25,8,8,25);
            TextView noHighScores = new TextView(this);
            //set the layout params of the text view
            noHighScores.setLayoutParams(lParams);
            noHighScores.setTextSize(16);
            //add the content
            noHighScores.setText("There are no highscores to highlight here.");
            //add the view to the layout
            li.addView(noHighScores);
        }
    }

    //display the contacts in a toast from the cursor values
    private void displayHighscores(Cursor c, int count)
    {
        //make sure you can only display 10 scores
        //less than or equal to nine means 0-9 which means 10 scores
        if(count <= 9)
        {
            //set the layout params of the text view
            ActionBar.LayoutParams lParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lParams.setMargins(25,8,8,25);
            LinearLayout li2 = new LinearLayout(this);
            li2.setLayoutParams(lParams);
            TextView highScores = new TextView(this);
            //set the layout params of the text view
            highScores.setLayoutParams(lParams);
            highScores.setPadding(10,10,0,10);
            highScores.setTextSize(16);
            //add the content
            highScores.setText("Name: " + c.getString(1) + " " + "Score: " + c.getString(2) + " " + "Success Counter: " + c.getString(3));
            //add the view to the layout
            highScores.startAnimation(animate.animate("slideUp"));
            li2.addView(highScores);
            li.addView(li2);
        }
    }
}
