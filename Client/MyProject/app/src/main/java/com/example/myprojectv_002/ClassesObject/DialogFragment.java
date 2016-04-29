package com.example.myprojectv_002.ClassesObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class DialogFragment extends android.support.v4.app.DialogFragment {

    String strTitle;
    String strMessage;

    public DialogFragment(String strTitle, String strMessage) {
        this.strTitle = strTitle;
        this.strMessage = strMessage;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        adb.setTitle(strTitle);
        if(!strMessage.equals("")) {
            adb.setMessage(strMessage);
        }
        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        return adb.create();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
