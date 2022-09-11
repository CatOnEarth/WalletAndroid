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
import com.snail.wallet.MainScreen.WalletActivity;
import com.snail.wallet.MainScreen.activities.AddActivity;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.R;
import com.snail.wallet.databinding.FragmentUserdataBinding;

import java.util.Objects;

public class UserDataFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();

    private FragmentUserdataBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserdataBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        PermanentStorage.init(getContext());

        TextView textViewUsername  = root.findViewById(R.id.textViewUserNameDataFragment);
        TextView textViewEmailUser = root.findViewById(R.id.textViewEmailUserDataFragment);

        textViewUsername.setText(PermanentStorage.getPropertyString(APP_PREFERENCES_USERNAME));
        textViewEmailUser.setText(PermanentStorage.getPropertyString(APP_PREFERENCES_USER_EMAIL));

        Button bLogOut = root.findViewById(R.id.bLogOutUserDataFragment);
        bLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                warningDialogAllDataClear();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void logOut() {
        PermanentStorage.init(getContext());
        PermanentStorage.clearPermanentStorage();

        AppDatabase db = App.getInstance().getAppDatabase();
        db.clearAllTables();

        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        startActivity(intent);

        requireActivity().finish();
    }

    private void warningDialogAllDataClear() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireActivity());

        alertDialog.setTitle("Вы точно хотите выйти?");
        alertDialog.setMessage("Вы потеряете все данные");

        alertDialog.setPositiveButton("Да",
                (dialog, which) -> logOut());

        alertDialog.setNegativeButton("Нет",
                (dialog, which) -> {
                    dialog.cancel();
                });

        alertDialog.show();
    }
}