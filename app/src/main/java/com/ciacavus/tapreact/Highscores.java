package com.ciacavus.tapreact;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ciaran on 13/06/2016.
 */
public class Highscores extends AppCompatActivity{

    //create new DB variable
    DBAdapter db;

    //get the layout of the screen
    LinearLayout li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.highscore_layout);

        //instantiate DBAdapter
        db = new DBAdapter(this);

        //get the layout
        li = (LinearLayout)findViewById(R.id.highscore);

        try{
            db.open();
            //create an SQL cursor for the functionality of getting all the contacts
            Cursor c = db.getAllInfo();

            int count = 0;

            //perform a query and in a do while loop,
            //run over each search result of the query
            if(c.moveToFirst())
            {
                do{
                    DisplayContacts(c,count);
                    count++;
                }
                while(c.moveToNext());


            }

            db.close();
        }catch (SQLException e)
        {

        }
    }

    //display the contacts in a toast from the cursor values
    private void DisplayContacts(Cursor c,int count)
    {
        //make sure you can only display 10 scores
        //less than or equal to nine means 0-9 which means 10 scores
        if(count <= 9)
        {
            //set the layout params of the text view
            ActionBar.LayoutParams lParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lParams.gravity = 0;
            TextView highScores = new TextView(this);
            //set the layout params of the text view
            highScores.setLayoutParams(lParams);
            highScores.setPadding(5,10,0,10);
            highScores.setTextSize(16);
            //add the content
            highScores.setText("Name: " + c.getString(1) + " " + "Score: " + c.getString(2));
            //add the view to the layout
            li.addView(highScores);
        }
    }
}
