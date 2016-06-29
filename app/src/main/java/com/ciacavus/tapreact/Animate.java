package com.ciacavus.tapreact;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by ciaran on 29/06/2016.
 */
public class Animate {

    //animations
    Animation slideIn;
    Animation slideOut;
    Animation slideUp;
    Animation wobble;

    public Animate(Context context)
    {
        //create animations
        slideIn = AnimationUtils.loadAnimation(context, R.anim.slide_in);
        slideOut = AnimationUtils.loadAnimation(context, R.anim.slide_out);
        slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        wobble = AnimationUtils.loadAnimation(context, R.anim.wobble_anim);
    }

    public Animation animate(String animationRequest)
    {
        switch (animationRequest)
        {
            case "slideIn":  return slideIn;
            case "slideUp": return slideUp;
            case "wobble": return wobble;
            default: return slideIn;
        }
    }
}
