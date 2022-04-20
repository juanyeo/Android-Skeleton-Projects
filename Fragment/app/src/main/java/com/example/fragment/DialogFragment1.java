package com.example.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DialogFragment1 extends DialogFragment {

    public DialogFragment1() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Dialog Fragment");
        builder.setMessage("Message: Fragment3");

        DialogListener listener = new DialogListener();
        builder.setPositiveButton("positive", listener);
        builder.setNegativeButton("negative",listener);

        AlertDialog alert = builder.create();

        return alert;
    }

    class DialogListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            MainActivity activity = (MainActivity) getActivity();
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:
                    activity.dialogResult("POSITIVE");
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    activity.dialogResult("NEGATIVE");
                    break;
            }
        }
    }
}