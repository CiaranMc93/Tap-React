package com.ciacavus.tapreact;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    Button userLogin;
    Button userRegister;
    Button nonMember;

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
        userLogin = (Button) findViewById(R.id.userlogin);
        userRegister = (Button) findViewById(R.id.userreg);
        nonMember = (Button) findViewById(R.id.nonmember);
        //start off as invisible and with no resource attached
        nonMember.setVisibility(View.INVISIBLE);
        nonMember.setBackgroundResource(0);

        //create the buttons onclick method
        //set up a button listener
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin.setVisibility(View.INVISIBLE);
                userRegister.setVisibility(View.INVISIBLE);
                //display redirect button
                nonMember.setVisibility(View.VISIBLE);
                userInput(true,true);
            }
        });

        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin.setVisibility(View.INVISIBLE);
                userRegister.setVisibility(View.INVISIBLE);
                userInput(true,false);
            }
        });
    }

    public void getUserData(Cursor c)
    {
        //get the first name in the database as a continued logged in user
        username = c.getString(0);
        password = c.getString(1);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void userInput(Boolean display, final Boolean whichDisplay)
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

            li2.setOrientation(LinearLayout.VERTICAL);
            //set the layout params of the text view
            userLogin.setLayoutParams(lParams);
            password.setLayoutParams(lParams);
            //add confirm password edittext if it is register flag
            if(!whichDisplay)
            {
                final EditText confirmPassword = new EditText(this);
                confirmPassword.setLayoutParams(lParams);
                confirmPassword.setHint("Confirm Password");
                li2.addView(confirmPassword);
            }

            //create submit button
            Button submit = new Button(this);

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

                    if(!whichDisplay)
                    {
                        String confirmPwd = password.getText().toString();

                        if(confirmPwd.contentEquals(pwd))
                        {
                            //re-open the database
                            db.open();

                            //register the user
                            if(db.registerUser(usr,pwd))
                            {
                                Toast.makeText(PersonalStats.this,"Registered!",Toast.LENGTH_SHORT).show();
                                db.close();

                            }
                            else
                            {
                                Toast.makeText(PersonalStats.this,"Unable to Register, Try Again!",Toast.LENGTH_SHORT).show();

                                db.close();
                            }
                        }
                        else
                        {
                            Toast.makeText(PersonalStats.this,"Passwords do not match",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        //just log the user in
                        db.open();
                        //create an SQL cursor for the functionality of getting all the contacts
                        Cursor c = db.getAllInfo("Login");

                        //perform a query and in a do while loop,
                        //run over each search result of the query
                        if(c.moveToFirst())
                        {
                            do{
                                //check if the username and password are in the database
                                if(c.getString(0).toString().contentEquals(usr) && c.getString(1).toString().contentEquals(pwd))
                                {
                                    Toast.makeText(PersonalStats.this,"Logged In!",Toast.LENGTH_SHORT).show();
                                }
                            }
                            while(c.moveToNext());


                        }

                        db.close();
                    }
                }
            });
        }
    }

}
