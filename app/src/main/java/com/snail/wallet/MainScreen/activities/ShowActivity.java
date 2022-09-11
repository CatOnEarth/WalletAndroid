package com.snail.wallet.MainScreen.activities;

import static com.snail.wallet.MainScreen.activities.AddActivity.ADDING_EXPENSES;
import static com.snail.wallet.MainScreen.activities.AddActivity.ADDING_OBJECT;
import static com.snail.wallet.MainScreen.activities.AddActivity.ADDING_REVENUE;
import static com.snail.wallet.MainScreen.activities.AddActivity.CATEGORY_EDITING;
import static com.snail.wallet.MainScreen.activities.AddActivity.CURRENCY_EDITING;
import static com.snail.wallet.MainScreen.activities.AddActivity.DESC_EDITING;
import static com.snail.wallet.MainScreen.activities.AddActivity.ID_EDITING;
import static com.snail.wallet.MainScreen.activities.AddActivity.STORAGE_LOCATION_EDITING;
import static com.snail.wallet.MainScreen.activities.AddActivity.VALUE_EDITING;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.CategoryDAO.CategoryDAO;
import com.snail.wallet.MainScreen.db.CurrencyDAO.CurrencyDAO;
import com.snail.wallet.MainScreen.db.ExpensesDAO.ExpensesDAO;
import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;
import com.snail.wallet.MainScreen.db.StorageLocationDAO.StorageLocationDAO;
import com.snail.wallet.MainScreen.models.money.Expenses;
import com.snail.wallet.MainScreen.models.money.Revenues;
import com.snail.wallet.MainScreen.models.parametrs.Date;
import com.snail.wallet.R;

import java.text.DecimalFormat;

public class ShowActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    public static final String ID_ITEM = "id_item";

    private double value_double;
    private String desc_str;
    private int category_id;
    private int storage_location_id;
    private int currency_id;

    private int id_item;
    private int type_item;

    TextView textViewCategory;
    TextView textViewValue;
    TextView textViewData;
    TextView textViewStorageLocation;
    TextView textViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Intent intent = getIntent();
        id_item       = intent.getIntExtra(ID_ITEM, -1);
        type_item     = intent.getIntExtra(ADDING_OBJECT, -1);
        if (id_item == -1 || type_item == -1) {
            finish();
        }

        initFindView();
        initData();

        initActionBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        Log.i(TAG, "Start onCreateOptionsMenu method");
        getMenuInflater().inflate(R.menu.menu_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "Start onOptionsItemSelected method");
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menuEdit) {
            startEditingActivity();
        } else if (item.getItemId() == R.id.menuDelete) {
            deleteDialog();
        }

        return(super.onOptionsItemSelected(item));
    }

    private void deleteItem() {
        AppDatabase db = App.getInstance().getAppDatabase();

        if (type_item == ADDING_REVENUE) {
            RevenueDAO revenueDAO = db.revenueDAO();
            revenueDAO.deleteById(id_item);
        } else if (type_item == ADDING_EXPENSES) {
            ExpensesDAO expensesDAO = db.expensesDAO();
            expensesDAO.deleteById(id_item);
        }

        finish();
    }

    private void deleteDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShowActivity.this);

        alertDialog.setTitle("Удалить?");
        alertDialog.setMessage("Вы точно хотите удалить?");

        alertDialog.setPositiveButton("Да",
                (dialog, which) -> deleteItem());

        alertDialog.setNegativeButton("Нет",
                (dialog, which) -> dialog.cancel());

        alertDialog.show();
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

    private void initFindView() {
        textViewCategory        = findViewById(R.id.textViewCategoryShow);
        textViewData            = findViewById(R.id.textViewDataShow);
        textViewValue           = findViewById(R.id.textViewValueShow);
        textViewDescription     = findViewById(R.id.textViewDescriptionShow);
        textViewStorageLocation = findViewById(R.id.textViewStorageLocationShow);

        if (type_item == ADDING_EXPENSES) {
            textViewStorageLocation.setVisibility(View.INVISIBLE);
        }
    }

    private void initData() {
        AppDatabase db = App.getInstance().getAppDatabase();

        if (type_item == ADDING_REVENUE) {
            initDataRevenue(db);
        } else if (type_item == ADDING_EXPENSES) {
            initDataExpenses(db);
        }
    }

    private void initDataRevenue(AppDatabase db) {
        StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
        CategoryDAO categoryDAO               = db.categoryDAO();
        CurrencyDAO currencyDAO               = db.currencyDAO();
        RevenueDAO  revenueDAO                = db.revenueDAO();
        Date        custom_date               = revenueDAO.getDateById(id_item);
        Revenues    revenues                  = revenueDAO.getById(id_item);

        category_id = revenues.getCategory();
        String category        = getResources().getText(R.string.textCategory) + ": " +
                                   categoryDAO.getCategoryById(revenues.getCategory()).getName();

        storage_location_id = revenues.getStorage_location();
        String storageLocation = getResources().getText(R.string.textStorageLocation) + ": " +
                                   storageLocationDAO.getLocationById(revenues.getStorage_location());

        DecimalFormat precision = new DecimalFormat("0.00");
        value_double = revenues.getValue();
        currency_id  = revenues.getCurrency();
        String value           = getResources().getText(R.string.textValue) + ": " + precision.format(value_double)
                                    + currencyDAO.getCurrencyById(revenues.getCurrency()).getSymbol();

        String date_item       = getResources().getText(R.string.textData) + ": " + custom_date.getDate_day() + "." +
                                   custom_date.getDate_month() + "." + custom_date.getDate_year();

        desc_str = revenues.getDescription();
        String description     = getResources().getText(R.string.HintDescription) + ": " + desc_str;

        textViewCategory.setText(category);
        textViewStorageLocation.setText(storageLocation);
        textViewData.setText(date_item);
        textViewValue.setText(value);
        textViewDescription.setText(description);
    }

    private void initDataExpenses(AppDatabase db) {
        CategoryDAO categoryDAO               = db.categoryDAO();
        CurrencyDAO currencyDAO               = db.currencyDAO();
        ExpensesDAO expensesDAO               = db.expensesDAO();
        Date        custom_date               = expensesDAO.getDateById(id_item);
        Expenses    expenses                  = expensesDAO.getById(id_item);

        category_id = expenses.getCategory();
        String category        = getResources().getText(R.string.textCategory) + ": " +
                categoryDAO.getCategoryById(expenses.getCategory()).getName();

        DecimalFormat precision = new DecimalFormat("0.00");
        value_double = expenses.getValue();
        currency_id  = expenses.getCurrency();
        String value           = getResources().getText(R.string.textValue) + ": " + precision.format(value_double) +
                                        currencyDAO.getCurrencyById(expenses.getCurrency()).getSymbol();

        String date_item       = getResources().getText(R.string.textData) + ": " + custom_date.getDate_day() + "." +
                custom_date.getDate_month() + "." + custom_date.getDate_year();

        desc_str = expenses.getDescription();
        String description     = getResources().getText(R.string.HintDescription) + ": " + desc_str;

        textViewCategory.setText(category);
        textViewData.setText(date_item);
        textViewValue.setText(value);
        textViewDescription.setText(description);
    }

    private void startEditingActivity() {
        Intent intent = new Intent(ShowActivity.this, AddActivity.class);
        intent.putExtra(ID_EDITING, id_item);
        intent.putExtra(ADDING_OBJECT, type_item);
        intent.putExtra(VALUE_EDITING, value_double);
        intent.putExtra(DESC_EDITING, desc_str);
        intent.putExtra(CATEGORY_EDITING, category_id);
        intent.putExtra(CURRENCY_EDITING, currency_id);

        if (type_item == ADDING_REVENUE) {
            intent.putExtra(STORAGE_LOCATION_EDITING, storage_location_id);
        }

        startActivity(intent);
    }
}