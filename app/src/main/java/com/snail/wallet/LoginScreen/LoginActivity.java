package com.snail.wallet.LoginScreen;

import static com.snail.wallet.WalletConstants.APP_PREFERENCES_IS_INIT_DB;
import static com.snail.wallet.WalletConstants.APP_PREFERENCES_IS_USER_LOG;
import static com.snail.wallet.WalletConstants.APP_PREFERENCES_USERNAME;
import static com.snail.wallet.WalletConstants.APP_PREFERENCES_USER_EMAIL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.snail.wallet.MainScreen.SharedPrefManager.PermanentStorage;
import com.snail.wallet.MainScreen.WalletActivity;
import com.snail.wallet.MainScreen.ui.dialogs.InfoDialogButtonListener;
import com.snail.wallet.MainScreen.ui.dialogs.InfoDialogFragment;
import com.snail.wallet.R;

public class LoginActivity extends AppCompatActivity implements InfoDialogButtonListener {
    private final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Start onCreate method LoginActivity");

        PermanentStorage.init(this);
        if (PermanentStorage.getPropertyBoolean(APP_PREFERENCES_IS_USER_LOG)) {
            startOfflineApp();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        } else {
            Log.e(TAG, "ActionBar was null");
            finish();
        }

        Button bLogIn             = findViewById(R.id.buttonLogIn);
        TextView bTextViewReg     = findViewById(R.id.textViewRegistration);
        TextView bTextViewOffline = findViewById(R.id.textViewOffline);

        bLogIn.setOnClickListener(view -> Log.i(TAG, "Press logIn button LoginActivity"));

        bTextViewReg.setOnClickListener(view -> Log.i(TAG, "Press TextViewReg text LoginActivity"));

        bTextViewOffline.setOnClickListener(view -> {
            Log.i(TAG, "Press TextViewOffline text LoginActivity");
            setLocalUser();
            startOfflineApp();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        Log.i(TAG, "Start onCreateOptionsMenu method LoginActivity");
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "Start onOptionsItemSelected method LoginActivity");
        if (item.getItemId() == R.id.about_login_activity) {
            Log.i(TAG, "About choose in menu actionBar");
            aboutDialog();
        }

        return(super.onOptionsItemSelected(item));
    }

    public void setLocalUser() {
        PermanentStorage.addPropertyString(APP_PREFERENCES_USERNAME, "local");
        PermanentStorage.addPropertyString(APP_PREFERENCES_USER_EMAIL, "local@local.ru");
        PermanentStorage.addPropertyBoolean(APP_PREFERENCES_IS_USER_LOG, true);
        PermanentStorage.addPropertyBoolean(APP_PREFERENCES_IS_INIT_DB, false);
    }

    public void startOfflineApp() {
        Intent intent = new Intent(LoginActivity.this, WalletActivity.class);
        intent.putExtra(APP_PREFERENCES_USERNAME, "local");
        intent.putExtra(APP_PREFERENCES_USER_EMAIL, "local@local.ru");
        startActivity(intent);
        finish();
    }

    private void aboutDialog() {
        InfoDialogFragment dialog = new InfoDialogFragment("О приложении", "Информация о приолжении",
                true, "Ок", false, "");

        dialog.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void InfoDialogPositiveButton() {

    }

    @Override
    public void InfoDialogNegativeButton() {

    }
}
