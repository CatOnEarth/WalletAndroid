package com.snail.wallet.LoginScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.snail.wallet.MainScreen.WalletActivity;
import com.snail.wallet.R;

/** Login activity class */
public class LoginActivity extends AppCompatActivity {
    /**  */
    private final String TAG = this.getClass().getSimpleName();

    /**  */
    private SharedPreferences mSettings;
    /**  */
    public static final String APP_PREFERENCES            = "user_info";
    /** Key to save username in preferences */
    public static final String APP_PREFERENCES_USERNAME   = "user_name";
    /** Key to save user's email in preferences */
    public static final String APP_PREFERENCES_USER_EMAIL = "user_email";

    /**onCreate method of LoginActivity
     *
     * @param savedInstanceState a reference to a Bundle object that is passed into the onCreate method of every Android Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Start onCreate method LoginActivity");

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
            int res = startOfflineApp();
        });

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        Log.i(TAG, "Start onCreateOptionsMenu method LoginActivity");
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    /**
     * 
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "Start onOptionsItemSelected method LoginActivity");
        if (item.getItemId() == R.id.about) {
            Log.i(TAG, "About choose in menu actionBar");
            return (true);
        }

        return(super.onOptionsItemSelected(item));
    }

    public int startOfflineApp() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_USERNAME,   "Local");
        editor.putString(APP_PREFERENCES_USER_EMAIL, "Local@local.ru");
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, WalletActivity.class);
        startActivity(intent);
        finish();

        return 0;
    }
}