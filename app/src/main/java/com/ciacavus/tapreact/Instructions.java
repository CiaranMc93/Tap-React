package com.ciacavus.tapreact;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ciaran on 21/06/2016.
 */
public class Instructions extends AppCompatActivity implements GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener{

    RelativeLayout instructionsBox;
    TextView instructList;
    int instructionVar = 1;

    TextView instructText;

    //animation
    Animate animate;

    //create gesture variable/physical activites
    GestureDetectorCompat mDetector;

    //instructions
    public String[] instructions = {"To play the game, you must perform different actions based on the phone.",
            "If you perform an action incorrectly, you lose the game and your score is calculated.",
            "Be careful to be deliberate with your actions as the screen is sensitive.",
            "Your auto-rotate function in your phone needs to be turned on.",
            "In order to gain the best score, you must perform as many actions as possible in a row.",
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

        //create a new animation
        animate = new Animate(this);

        setInstruction(instructionVar,instructionVar-1);

        Toast.makeText(Instructions.this,"Swipe left or right to get instructions",Toast.LENGTH_SHORT).show();

        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this, this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);
    }

    public void setInstruction(int index, int instruct)
    {
        //make the instruction box be navigatable through the array
        instructList.setText(index + "/" + instructions.length);

        //set the layout params of the text view
        ActionBar.LayoutParams lParams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        instructText = new TextView(this);
        //set the layout params of the text view
        instructText.setLayoutParams(lParams);
        instructText.setTextSize(30);
        instructText.setPadding(30,30,30,30);
        //add the content
        instructText.startAnimation(animate.animate("slideIn"));
        instructText.setText(instructions[instruct]);
        //add the view to the layout
        instructionsBox.addView(instructText);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        //make sure that the x-axis variable has a significant value
        //if a person moves their finger right the x-axis is updated and vice versa for y-axis
        if(velocityX > 100.000)
        {
            instructionVar++;

            //if the index is more than 5, change it back to 1
            //make sure that there is no index out of bounds issue
            if(instructionVar > 6)
            {
                instructionVar = 1;
            }

            //update the instructions
            instructionsBox.removeView(instructText);
            setInstruction(instructionVar,instructionVar-1);
        }
        //make sure that the x-axis variable has a significant value
        else if(velocityX < 100.000)
        {
            instructionVar--;

            //make sure that there is no index out of bounds issue
            if(instructionVar < 1)
            {
                instructionVar = 6;
            }

            //update the instructions
            instructionsBox.removeView(instructText);
            setInstruction(instructionVar,instructionVar-1);
        }

        return true;
    }
}
