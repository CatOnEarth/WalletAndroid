package com.snail.wallet.MainScreen.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditTextDialogFragment extends DialogFragment {
    private EditTextDialogButtonListener editTextDialogButtonListener;
    private final Context context;

    private final String  title;
    private final String  message;
    private final boolean is_pos_button;
    private final String  pos_button;
    private final boolean is_neg_button;
    private final String  neg_button;

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        editTextDialogButtonListener = (EditTextDialogButtonListener) context;
    }

    public EditTextDialogFragment(Context context, String title, String message,
                                  boolean is_pos_button, String pos_button, boolean is_neg_button,
                                  String neg_button) {

        this.context       = context;
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

        final EditText input = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        int maxLength        = 32;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0]            = new InputFilter.LengthFilter(maxLength);
        input.setFilters(fArray);
        input.setLayoutParams(lp);
        builder.setView(input);

        if (is_pos_button) {
            builder.setPositiveButton(pos_button, (dialog, which) -> editTextDialogButtonListener
                    .EditTextDialogPositiveButton(input.getText().toString()));
        }
        if (is_neg_button) {
            builder.setNegativeButton(neg_button, (dialog, which) -> editTextDialogButtonListener
                    .EditTextDialogNegativeButton(input.getText().toString()));
        }

        return builder.create();
    }
}
