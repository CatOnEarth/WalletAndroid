package com.snail.wallet.MainScreen.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class InfoDialogFragment extends DialogFragment {

    private InfoDialogButtonListener infoDialogButtonListener;

    private final String  title;
    private final String  message;
    private final boolean is_pos_button;
    private final String  pos_button;
    private final boolean is_neg_button;
    private final String  neg_button;

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        infoDialogButtonListener = (InfoDialogButtonListener) context;
    }

    public InfoDialogFragment(String title, String message, boolean is_pos_button,
                              String pos_button, boolean is_neg_button, String neg_button) {

        this.title         = title;
        this.message       = message;
        this.is_pos_button = is_pos_button;
        this.pos_button    = pos_button;
        this.is_neg_button = is_neg_button;
        this.neg_button    = neg_button;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(title)
                .setMessage(message);

        if (is_pos_button) {
            builder.setPositiveButton(pos_button, (dialog, which) -> infoDialogButtonListener
                                                                        .InfoDialogPositiveButton());
        }
        if (is_neg_button) {
            builder.setNegativeButton(neg_button, (dialog, which) -> infoDialogButtonListener
                                                                        .InfoDialogNegativeButton());
        }

        return builder.create();
    }
}
