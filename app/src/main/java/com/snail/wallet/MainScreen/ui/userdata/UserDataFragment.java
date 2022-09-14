package com.snail.wallet.MainScreen.ui.userdata;

import static com.snail.wallet.WalletConstants.APP_PREFERENCES_USERNAME;
import static com.snail.wallet.WalletConstants.APP_PREFERENCES_USER_EMAIL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.snail.wallet.LoginScreen.LoginActivity;
import com.snail.wallet.MainScreen.SharedPrefManager.PermanentStorage;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.databinding.FragmentUserdataBinding;


public class UserDataFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    private FragmentUserdataBinding binding;

    private TextView textViewUsername;
    private TextView textViewEmailUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView method");

        binding = FragmentUserdataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initViews();
        initUserData();

        return root;
    }

    private void initUserData() {
        Log.d(TAG, "initUserData method");

        PermanentStorage.init(requireContext());

        textViewUsername.setText(PermanentStorage.getPropertyString(APP_PREFERENCES_USERNAME));
        textViewEmailUser.setText(PermanentStorage.getPropertyString(APP_PREFERENCES_USER_EMAIL));
    }

    private void initViews() {
        Log.d(TAG, "initViews method");

        textViewUsername  = binding.textViewUserDataFragmentUserName;
        textViewEmailUser = binding.textViewUserDataFragmentEmail;

        Button bLogOut    = binding.buttonUserDataFragmentLogOut;
        bLogOut.setOnClickListener(view -> warningDialogAllDataClear());
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView method");
        super.onDestroyView();
        binding = null;
    }

    private void logOut() {
        Log.d(TAG, "logOut method");

        PermanentStorage.init(requireContext());
        PermanentStorage.clearPermanentStorage();

        AppDatabase db = App.getInstance().getAppDatabase();
        db.clearAllTables();

        Intent intent  = new Intent(requireActivity(), LoginActivity.class);
        startActivity(intent);

        requireActivity().finish();
    }

    private void warningDialogAllDataClear() {
        Log.d(TAG, "warningDialogAllDataClear method");

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireActivity());

        alertDialog.setTitle("Вы точно хотите выйти?");
        alertDialog.setMessage("Вы потеряете все данные");

        alertDialog.setPositiveButton("Да",
                (dialog, which) -> logOut());

        alertDialog.setNegativeButton("Нет",
                (dialog, which) -> dialog.cancel());

        alertDialog.show();
    }
}
