package com.snail.wallet.LoginScreen;

import static com.snail.wallet.WalletConstants.APP_PREFERENCES_IS_INIT_DB;
import static com.snail.wallet.WalletConstants.APP_PREFERENCES_IS_INIT_RATES;
import static com.snail.wallet.WalletConstants.APP_PREFERENCES_IS_USER_LOG;
import static com.snail.wallet.WalletConstants.APP_PREFERENCES_USERNAME;
import static com.snail.wallet.WalletConstants.APP_PREFERENCES_USER_EMAIL;
import static com.snail.wallet.WalletConstants.CODE_ABOUT_DIALOG;

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
import com.snail.wallet.MainScreen.ui.dialogs.InfoDialogFragment;
import com.snail.wallet.R;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate method");

        isUserLogOn();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initActionBar();

        initViews();
    }

    private void initViews() {
        Log.d(TAG, "initViews method");

        Button   bLogIn           = findViewById(R.id.buttonLogIn);
        TextView bTextViewReg     = findViewById(R.id.textViewRegistration);
        TextView bTextViewOffline = findViewById(R.id.textViewOffline);

        bLogIn.setOnClickListener(view -> Log.d(TAG, "Press logIn button"));

        bTextViewReg.setOnClickListener(view -> Log.d(TAG, "Press TextViewReg text"));

        bTextViewOffline.setOnClickListener(view -> {
            Log.d(TAG, "Press TextViewOffline text LoginActivity");
            setLocalUser();
            startOfflineApp();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu method");

        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected method");

        if (item.getItemId() == R.id.about_login_activity) {
            Log.d(TAG, "About choose in menu actionBar");
            aboutDialog();
        } else {
            Log.w(TAG, "Choose some item in menu, but can't find this view elem");
        }

        return(super.onOptionsItemSelected(item));
    }

    public void initActionBar() {
        Log.d(TAG, "initActionBar method");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        } else {
            Log.e(TAG, "ActionBar was null");
            finish();
            return;
        }

        Log.d(TAG, "ActionBar init");
    }

    public void isUserLogOn() {
        Log.d(TAG, "isUserLogOn method");

        PermanentStorage.init(this);
        if (PermanentStorage.getPropertyBoolean(APP_PREFERENCES_IS_USER_LOG)) {
            Log.i(TAG, "User already LogIn");
            startOfflineApp();
        }

        Log.i(TAG, "User not LogIn before this");
    }

    public void setLocalUser() {
        Log.d(TAG, "setLocalUser method");

        PermanentStorage.addPropertyString(APP_PREFERENCES_USERNAME, "local");
        PermanentStorage.addPropertyString(APP_PREFERENCES_USER_EMAIL, "local@local.ru");
        PermanentStorage.addPropertyBoolean(APP_PREFERENCES_IS_USER_LOG, true);
        PermanentStorage.addPropertyBoolean(APP_PREFERENCES_IS_INIT_DB, false);
        PermanentStorage.addPropertyBoolean(APP_PREFERENCES_IS_INIT_RATES, false);
    }

    public void startOfflineApp() {
        Log.d(TAG, "startOfflineApp method");

        Intent intent = new Intent(LoginActivity.this, WalletActivity.class);
        intent.putExtra(APP_PREFERENCES_USERNAME, "local");
        intent.putExtra(APP_PREFERENCES_USER_EMAIL, "local@local.ru");
        startActivity(intent);
        finish();
    }

    private void aboutDialog() {
        Log.d(TAG, "aboutDialog method");

        InfoDialogFragment dialog = new InfoDialogFragment(CODE_ABOUT_DIALOG, "О приложении",
                "Информация о приолжении", true, "Ок",
                false, "");

        dialog.show(getSupportFragmentManager(), TAG);
    }
}
