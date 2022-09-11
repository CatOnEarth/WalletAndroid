package com.snail.wallet.MainScreen.activities;

import static com.snail.wallet.WalletConstants.ADDING_OBJ_EXPENSES_TYPE;
import static com.snail.wallet.WalletConstants.ADDING_OBJ_REVENUE_TYPE;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CATEGORY_EXPENSES;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CATEGORY_REVENUE;
import static com.snail.wallet.WalletConstants.CODE_TYPE_STORAGE_LOCATION;
import static com.snail.wallet.WalletConstants.SETTING_TYPE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.CategoryDAO.CategoryDAO;
import com.snail.wallet.MainScreen.db.StorageLocationDAO.StorageLocationDAO;
import com.snail.wallet.MainScreen.ui.adapters.SettingsRecyclerViewAdapter;
import com.snail.wallet.R;

import java.util.ArrayList;
import java.util.List;

public class EditSettingActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private int typeSetting;

    private RecyclerView                recyclerViewSettings;
    private SettingsRecyclerViewAdapter settingsRecyclerViewAdapter;
    private List                        settingsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_setting);

        settingsData = new ArrayList();

        initActionBar();
        getIntentData();
        initData();

        recyclerViewSettings = findViewById(R.id.recyclerViewSetting);

        settingsRecyclerViewAdapter = new SettingsRecyclerViewAdapter(typeSetting, settingsData, this);

        recyclerViewSettings.setAdapter(settingsRecyclerViewAdapter);

        recyclerViewSettings.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "Start onOptionsItemSelected method");
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        } else {
            Log.e(TAG, "ActionBar was null");
            finish();
        }
    }

    private void getIntentData() {
        Intent intent = getIntent();
        typeSetting = intent.getIntExtra(SETTING_TYPE, -1);
    }

    private void initData() {
        AppDatabase db = App.getInstance().getAppDatabase();

        if        (typeSetting == CODE_TYPE_CATEGORY_REVENUE) {
            CategoryDAO categoryDAO = db.categoryDAO();
            settingsData.addAll(categoryDAO.getCategoryByType(ADDING_OBJ_REVENUE_TYPE));
        } else if (typeSetting == CODE_TYPE_CATEGORY_EXPENSES) {
            CategoryDAO categoryDAO = db.categoryDAO();
            settingsData.addAll(categoryDAO.getCategoryByType(ADDING_OBJ_EXPENSES_TYPE));
        } else if (typeSetting == CODE_TYPE_STORAGE_LOCATION) {
            StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
            settingsData.addAll(storageLocationDAO.getAll());
        }
    }
}