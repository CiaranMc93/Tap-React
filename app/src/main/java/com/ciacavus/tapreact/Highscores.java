package com.ciacavus.tapreact;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by ciaran on 13/06/2016.
 */
public class Highscores extends AppCompatActivity{

    //create new DB variable
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.highscore_layout);

        db.open();

        try{
            //create an SQL cursor for the functionality of getting all the contacts
            Cursor c = db.getAllScores();

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
        }catch (SQLException e)
        {

        }
    }

    //display the contacts in a toast from the cursor values
    private void DisplayContacts(Cursor c,int count)
    {
        //add multiple text views to the layout to be shown
    }
}
