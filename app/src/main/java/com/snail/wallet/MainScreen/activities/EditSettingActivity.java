package com.snail.wallet.MainScreen.activities;

import static com.snail.wallet.WalletConstants.ADDING_OBJ_EXPENSES_TYPE;
import static com.snail.wallet.WalletConstants.ADDING_OBJ_REVENUE_TYPE;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CATEGORY_EXPENSES;
import static com.snail.wallet.WalletConstants.CODE_TYPE_CATEGORY_REVENUE;
import static com.snail.wallet.WalletConstants.CODE_TYPE_STORAGE_LOCATION;
import static com.snail.wallet.WalletConstants.SETTING_TYPE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.CategoryDAO.CategoryDAO;
import com.snail.wallet.MainScreen.db.StorageLocationDAO.StorageLocationDAO;
import com.snail.wallet.MainScreen.models.parametrs.Category;
import com.snail.wallet.MainScreen.models.parametrs.StorageLocation;
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
        initViews();
        initRecyclerView();
    }

    private void initRecyclerView() {
        settingsRecyclerViewAdapter = new SettingsRecyclerViewAdapter(typeSetting, settingsData, this);
        recyclerViewSettings.setAdapter(settingsRecyclerViewAdapter);
        recyclerViewSettings.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initViews() {
        recyclerViewSettings = findViewById(R.id.recyclerViewSetting);

        FloatingActionButton bAddNewSetting = findViewById(R.id.floatingActionButtonAddNewSetting);
        bAddNewSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogNewSetting();
            }
        });
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
        settingsData.clear();

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

    private void dialogNewSetting() {
        Log.d(TAG, "dialogNewSetting method");

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Название")
                .setMessage("Введите новое название(макс. 32 символа)");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        int maxLength = 32;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        input.setFilters(fArray);
        input.setLayoutParams(lp);
        builder.setView(input);

        builder
                .setPositiveButton(android.R.string.yes, (dialog, which) -> addNewSetting(input.getText().toString()))
                .setNegativeButton(android.R.string.no, null)
                .show();

    }

    private void addNewSetting(String new_elem) {
        Log.d(TAG, "addNewSetting method = ");

        if (new_elem.length() == 0) {
            Toast.makeText(this, "Пустое новое название недопустимо", Toast.LENGTH_SHORT).show();
        }

        AppDatabase db = App.getInstance().getAppDatabase();

        if (typeSetting == CODE_TYPE_CATEGORY_REVENUE || typeSetting == CODE_TYPE_CATEGORY_EXPENSES) {
            if (!isExistNewNameCategory(db, typeSetting, new_elem)) {
                addNewCategory(db, new_elem);
            } else {
                Toast.makeText(this, "Такая категория уже существует", Toast.LENGTH_SHORT).show();
            }
        } else if (typeSetting == CODE_TYPE_STORAGE_LOCATION) {
            if (!isExistNewNameStorageLocation(db, new_elem)) {
                addNewStorageLocation(db, new_elem);
            } else {
                Toast.makeText(this, "Такое место хранения уже существует", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.w(TAG, "unknown typeSetting = " + typeSetting);

        }
    }

    private void addNewStorageLocation(AppDatabase db, String new_elem) {
        StorageLocation storageLocation = new StorageLocation(new_elem);
        db.storageLocationDAO().insert(storageLocation);
        settingsData.add(storageLocation);
        settingsRecyclerViewAdapter.notifyItemInserted(settingsData.size() - 1);
    }

    private void addNewCategory(AppDatabase db, String new_elem) {
        Category new_category = new Category(typeSetting, new_elem);
        db.categoryDAO().insert(new_category);
        settingsData.add(new_category);
        settingsRecyclerViewAdapter.notifyItemInserted(settingsData.size() - 1);
    }

    private boolean isExistNewNameCategory(AppDatabase db, int type_category, String new_name) {
        Log.d(TAG, "isExistNewNameCategory method");

        CategoryDAO categoryDAO = db.categoryDAO();
        return categoryDAO.getByNameAndTypeCategory(type_category, new_name).size() != 0;
    }

    private boolean isExistNewNameStorageLocation(AppDatabase db, String new_name) {
        Log.d(TAG, "isExistNewNameStorageLocation method");

        StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
        return storageLocationDAO.getLocationByName(new_name).size() != 0;
    }
}