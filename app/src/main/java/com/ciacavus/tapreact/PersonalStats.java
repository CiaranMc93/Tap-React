package com.ciacavus.tapreact;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by ciaran on 21/06/2016.
 */
public class PersonalStats extends AppCompatActivity{

    //this information is defaulted
    public static String username;
    public static String password;

    //get the layout of the screen
    LinearLayout li;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //hide the action bar from this activity
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.personal_stats);

        db = new DBAdapter(this);
        li = (LinearLayout)findViewById(R.id.login);

        //initial login
        //check the user login
        db.open();

        Cursor c = db.getFirstUser();

        //perform a query and in a do while loop,
        //run over each search result of the query
        if(c.moveToFirst())
        {
            //get from database
            getUserData(c);
        }
        else
        {
            //else default the login to a basic value
            username = " ";
            password = " ";
        }

        if(db.loginUser(username,password))
        {
            //toast the user
            Toast.makeText(PersonalStats.this,"Welcome, " + username,Toast.LENGTH_SHORT).show();
        }
        else
        {
            //call the user login function
            userRegister(true);
        }

        db.close();
    }

    public void getUserData(Cursor c)
    {
        //get the first name in the database as a continued logged in user
        username = c.getString(0);
        password = c.getString(1);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void userRegister(Boolean display)
    {
        //check the flag
        if(display)
        {
            //set the layout params of the views
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams relativeParams= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout li2 = new LinearLayout(this);
            final EditText userLogin = new EditText(this);
            final EditText password = new EditText(this);
            Button submit = new Button(this);

            li2.setOrientation(LinearLayout.VERTICAL);
            //set the layout params of the text view
            userLogin.setLayoutParams(lParams);
            password.setLayoutParams(lParams);
            //set margins
            lParams.setMargins(25,8,25,8);
            li2.setLayoutParams(lParams);
            relativeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            submit.setLayoutParams(relativeParams);

            //fill in the views
            submit.setText("Login");
            userLogin.setHint("User Name");
            password.setHint("Password");

            //add the view to the layout
            li2.addView(userLogin);
            li2.addView(password);
            li2.addView(submit);
            //set color of layout
            li2.setBackground(getResources().getDrawable(R.drawable.rounded_corners));

            //add login view to the linear layout
            li.addView(li2);

            //initialise the button and get the text from the edit texts
            submit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    String usr = userLogin.getText().toString();
                    String pwd = password.getText().toString();

                    //re-open the database
                    db.open();

                    //register the user
                    if(db.registerUser(usr,pwd))
                    {
                        Toast.makeText(PersonalStats.this,"Registered!",Toast.LENGTH_SHORT).show();
                        db.close();
                        userRegister(false);
                    }
                    else
                    {
                        Toast.makeText(PersonalStats.this,"Unable to Register, Try Again!",Toast.LENGTH_SHORT).show();
                        //recursive call if unsuccessful
                        userRegister(true);
                        db.close();
                    }
                }
            });
        }
    }

}
