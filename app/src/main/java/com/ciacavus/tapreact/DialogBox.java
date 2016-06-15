package com.ciacavus.tapreact;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by ciaran on 14/06/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DialogBox extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //construct dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Dialog Box").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        //return the builder dialog
        return builder.create();
    }
}
