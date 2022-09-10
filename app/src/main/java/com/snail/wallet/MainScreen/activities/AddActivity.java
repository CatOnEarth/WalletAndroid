package com.snail.wallet.MainScreen.activities;

import static com.snail.wallet.MainScreen.WalletActivity.TYPE_CATEGORY;
import static com.snail.wallet.MainScreen.WalletActivity.TYPE_CURRENCY;
import static com.snail.wallet.MainScreen.WalletActivity.TYPE_STORAGE_LOCATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.CategoryDAO.CategoryDAO;
import com.snail.wallet.MainScreen.db.CurrencyDAO.CurrencyDAO;
import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;
import com.snail.wallet.MainScreen.db.StorageLocationDAO.StorageLocationDAO;
import com.snail.wallet.MainScreen.models.parametrs.Category;
import com.snail.wallet.MainScreen.models.parametrs.Currency;
import com.snail.wallet.MainScreen.models.money.Revenues;
import com.snail.wallet.MainScreen.models.parametrs.StorageLocation;
import com.snail.wallet.MainScreen.ui.adapters.SpinnerAdapter;
import com.snail.wallet.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private Spinner spinnerCategory;
    private Spinner spinnerCurrency;
    private Spinner spinnerStorageLocation;

    private SpinnerAdapter categoryAdapter;
    private SpinnerAdapter storageLocationAdapter;

    private EditText editTextValue;
    private EditText editTextDescription;

    private TextView       date;
    private final Calendar dateAndTime = Calendar.getInstance();

    private Context ctxRevenueAdd;

    private ArrayList<Category>        categoryList;
    private ArrayList<StorageLocation> storageLocationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_add);

        ctxRevenueAdd = this;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        } else {
            Log.e(TAG, "ActionBar was null");
            finish();
        }

        initFindView();
        initSpinners();
        setInitialDateTime();
        initButtons();
    }

    private void initSpinners() {
        AppDatabase db          = App.getInstance().getAppDatabase();
        initCategorySpinner(db);
        initCurrencySpinner(db);
        initStorageLocationSpinner(db);
    }

    private void initFindView() {
        editTextValue       = findViewById(R.id.editTextNumberValueRevenue);
        editTextDescription = findViewById(R.id.editTextTextDescriptionRevenue);

        spinnerCategory        = findViewById(R.id.spinnerCategoryRevenue);
        spinnerCurrency        = findViewById(R.id.spinnerCurrencyRevenue);
        spinnerStorageLocation = findViewById(R.id.spinnerStorageLocationRevenue);

        date = findViewById(R.id.textViewDateSelectedRevenue);
    }

    private void initCategorySpinner(AppDatabase db) {
        CategoryDAO categoryDAO = db.categoryDAO();
        categoryList = new ArrayList<>();
        categoryList = (ArrayList<Category>) categoryDAO.getAll();
        categoryAdapter = new SpinnerAdapter( this, TYPE_CATEGORY, categoryList);
        spinnerCategory.setAdapter(categoryAdapter);
    }

    private void initCurrencySpinner(AppDatabase db) {
        CurrencyDAO currencyDAO = db.currencyDAO();
        ArrayList<Currency> currencyList = (ArrayList<Currency>) currencyDAO.getAll();
        SpinnerAdapter currencyAdapter = new SpinnerAdapter(this, TYPE_CURRENCY, currencyList);
        spinnerCurrency.setAdapter(currencyAdapter);
    }

    private void initStorageLocationSpinner(AppDatabase db) {
        StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
        storageLocationList = new ArrayList<>();
        storageLocationList.addAll(storageLocationDAO.getAll());
        storageLocationAdapter = new SpinnerAdapter(this, TYPE_STORAGE_LOCATION, storageLocationList);
        spinnerStorageLocation.setAdapter(storageLocationAdapter);
    }

    private void initButtons() {
        Button bAddCategory = findViewById(R.id.buttonAddCategory);
        bAddCategory.setOnClickListener(view -> addingDialog(TYPE_CATEGORY));

        Button bAddStorageLocation = findViewById(R.id.buttonAddStorageLocation);
        bAddStorageLocation.setOnClickListener(view -> addingDialog(TYPE_STORAGE_LOCATION));

        Button bSaveRevenue = findViewById(R.id.bAddRevenueSave);
        bSaveRevenue.setOnClickListener(view -> SaveRevenue());

        date.setOnClickListener(this::setDate);
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
        date.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(AddActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        dateAndTime.set(Calendar.YEAR, year);
        dateAndTime.set(Calendar.MONTH, monthOfYear);
        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        Log.i(TAG, "Start onCreateOptionsMenu method LoginActivity");
        getMenuInflater().inflate(R.menu.add_revenue_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "Start onOptionsItemSelected method");
        if (item.getItemId() == R.id.save) {
            Log.i(TAG, "save choose in menu actionBar");
            SaveRevenue();
        } else if (item.getItemId() == android.R.id.home) {
            exitDialog();
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        exitDialog();
    }

    private void SaveRevenue() {
        if (!IsAllCorrect()) return;

        int    category         = ((Category)spinnerCategory.getSelectedItem()).getId();
        String dateStr          = date.toString();
        String description      = editTextDescription.getText().toString();
        double value            = getValue();
        int    currency         = ((Currency)spinnerCurrency.getSelectedItem()).getId();
        int    storage_location = ((StorageLocation)spinnerStorageLocation.getSelectedItem()).getId();

        Revenues revenue = new Revenues(value, currency, category, dateStr, description, storage_location);

        AppDatabase db        = App.getInstance().getAppDatabase();
        RevenueDAO revenueDAO = db.revenueDAO();

        revenueDAO.insert(revenue);

        finish();
    }

    private boolean IsAllCorrect() {
        return IsCorrectValue();
    }

    private boolean IsCorrectValue() {
        String textValue = editTextValue.getText().toString();
        try {
            double value = Double.parseDouble(textValue);
            if (value <= 0) {
                editTextValue.setError("Введите сумму");
                return false;
            }
        } catch  (NumberFormatException ex) {
            Log.i(TAG, "catch exception age converting string to int");
            editTextValue.setError("Неправильно введена сумма");

            return false;
        }

        return true;
    }

    private double getValue() {
        return Double.parseDouble(editTextValue.getText().toString());
    }

    private void exitDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddActivity.this);

        alertDialog.setTitle("Сохранить?");
        alertDialog.setMessage("Сохранить введенные данные?");

        alertDialog.setPositiveButton("Да",
                (dialog, which) -> SaveRevenue());

        alertDialog.setNegativeButton("Нет",
                (dialog, which) -> {
                    dialog.cancel();
                    finish();
                });

        alertDialog.show();
    }

    private void addingDialog(int type) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddActivity.this);
        if        (type == TYPE_CATEGORY) {
            alertDialog.setTitle("Категория");
            alertDialog.setMessage("Добавить категорию");
        } else if (type == TYPE_STORAGE_LOCATION) {
            alertDialog.setTitle("Место хранения");
            alertDialog.setMessage("Добавить место хранения");
        } else {
            return;
        }

        final EditText input = new EditText(AddActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Добавить",
                (dialog, which) -> {
                    if (IsCorrectAdding(type, input.getText().toString())) {
                        addAddingVariable(type, input.getText().toString());
                        Toast.makeText(ctxRevenueAdd, "Успено добавлена", Toast.LENGTH_LONG).show();
                    }
                });

        alertDialog.setNegativeButton("Отмена",
                (dialog, which) -> dialog.cancel());

        alertDialog.show();
    }

    private void addAddingVariable(int type, String str) {
        AppDatabase db = App.getInstance().getAppDatabase();

        if        (type == TYPE_CATEGORY) {
            insertNewCategory(db, str);
        } else if (type == TYPE_STORAGE_LOCATION) {
            insertNewStorageLocation(db, str);
        }
    }

    private void insertNewCategory(AppDatabase db, String str) {
        CategoryDAO categoryDAO = db.categoryDAO();
        categoryDAO.insert(new Category(1, str));
        categoryList.clear();
        categoryList.addAll(categoryDAO.getAll());
        categoryAdapter.notifyDataSetChanged();
    }

    private void insertNewStorageLocation(AppDatabase db, String str) {
        StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
        storageLocationDAO.insert(new StorageLocation(str));
        storageLocationList.clear();
        storageLocationList.addAll(storageLocationDAO.getAll());
        storageLocationAdapter.notifyDataSetChanged();
    }

    private boolean IsCorrectAdding(int type, String str) {
        if (str.length() == 0) return false;

        AppDatabase db = App.getInstance().getAppDatabase();

         if       (type == TYPE_CATEGORY) {
            return db.categoryDAO().getCategoryName(str).size() == 0;
        } else if (type == TYPE_STORAGE_LOCATION) {
            return db.storageLocationDAO().getByStorageLocationName(str).size() == 0;
        } else {
            return false;
        }
    }
}