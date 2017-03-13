package com.example.sninkovic_ns.zad30c.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.example.sninkovic_ns.zad30c.R;


public class AboutDialog extends AlertDialog.Builder {

    public AboutDialog(Context context){
        super(context);


        setTitle("About");
        setMessage("Srdjan Ninkovic");
        setCancelable(false);


    setPositiveButton (R.string.dialog_about_yes, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.dismiss();
            }

        });
    setNegativeButton(R.string.dialog_about_no, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
            }
    });

    }

    public AlertDialog prepareDialog(){
        AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}
