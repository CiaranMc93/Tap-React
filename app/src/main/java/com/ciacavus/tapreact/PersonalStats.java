package com.ciacavus.tapreact;

import android.annotation.TargetApi;
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
    public static String username = "Ciacavus";
    public static String password = "C11354741";

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

        if(db.loginUser(username,password))
        {
            //toast the user
            Toast.makeText(PersonalStats.this,"Welcome, " + username,Toast.LENGTH_SHORT).show();
        }
        else
        {
            //call the user login function
            userRegister();
        }

        db.close();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void userRegister()
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
            }
        });
    }

}
