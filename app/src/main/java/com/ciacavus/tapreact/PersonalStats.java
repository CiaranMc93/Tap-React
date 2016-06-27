package com.ciacavus.tapreact;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ciaran on 21/06/2016.
 */
public class PersonalStats extends AppCompatActivity{

    //get the layout of the screen
    LinearLayout li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //hide the action bar from this activity
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.personal_stats);

        li = (LinearLayout)findViewById(R.id.login);

        //call the user login function
        userLogin();

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void userLogin()
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
