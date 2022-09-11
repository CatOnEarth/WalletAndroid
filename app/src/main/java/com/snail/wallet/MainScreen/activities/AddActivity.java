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
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
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
import com.snail.wallet.MainScreen.db.ExpensesDAO.ExpensesDAO;
import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;
import com.snail.wallet.MainScreen.db.StorageLocationDAO.StorageLocationDAO;
import com.snail.wallet.MainScreen.models.money.Expenses;
import com.snail.wallet.MainScreen.models.parametrs.Category;
import com.snail.wallet.MainScreen.models.parametrs.Currency;
import com.snail.wallet.MainScreen.models.money.Revenues;
import com.snail.wallet.MainScreen.models.parametrs.StorageLocation;
import com.snail.wallet.MainScreen.ui.adapters.SpinnerAdapter;
import com.snail.wallet.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    public static final String ID_EDITING    = "id_editing";
    public static final String VALUE_EDITING = "val_editing";
    public static final String DESC_EDITING  = "desc_editing";

    public static final String ADDING_OBJECT   = "obj_add";
    public static final int    ADDING_REVENUE  = 1;
    public static final int    ADDING_EXPENSES = 2;

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

    private int id_editing;

    private int type_adding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

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

        Intent intent = getIntent();
        type_adding   = intent.getIntExtra(ADDING_OBJECT, -1);
        id_editing    = intent.getIntExtra(ID_EDITING, -1);
        if (type_adding == -1 || id_editing == -1) {
            finish();
        }


        initFindView();
        initSpinners();
        setInitialDateTime();
        initButtons();
        if (id_editing != -1) {
            setEditingData(intent);
        }
    }

    private void initSpinners() {
        AppDatabase db          = App.getInstance().getAppDatabase();
        initCategorySpinner(db);
        initCurrencySpinner(db);

        if (type_adding == ADDING_REVENUE) {
            initStorageLocationSpinner(db);
        }
    }

    private void initFindView() {
        editTextValue       = findViewById(R.id.editTextNumberValueRevenue);
        editTextDescription = findViewById(R.id.editTextTextDescriptionRevenue);

        spinnerCategory        = findViewById(R.id.spinnerCategoryRevenue);
        spinnerCurrency        = findViewById(R.id.spinnerCurrencyRevenue);
        spinnerStorageLocation = findViewById(R.id.spinnerStorageLocationRevenue);

        if (type_adding == ADDING_EXPENSES) {
            TextView textSpinnerStorageLocation = findViewById(R.id.textViewStorageLocationRevenue);
            Button bAddStorageLocation = findViewById(R.id.buttonAddStorageLocation);
            spinnerStorageLocation.setVisibility(View.INVISIBLE);
            textSpinnerStorageLocation.setVisibility(View.INVISIBLE);
            bAddStorageLocation.setVisibility(View.INVISIBLE);
        }

        date                   = findViewById(R.id.textViewDateSelectedRevenue);
    }

    private void initCategorySpinner(AppDatabase db) {
        CategoryDAO categoryDAO = db.categoryDAO();
        categoryList = new ArrayList<>();
        categoryList = (ArrayList<Category>) categoryDAO.getCategoryByType(type_adding);
        categoryAdapter = new SpinnerAdapter( this, TYPE_CATEGORY, categoryList);
        spinnerCategory.setAdapter(categoryAdapter);
    }

    private void initCurrencySpinner(AppDatabase db) {
        CurrencyDAO currencyDAO = db.currencyDAO();
        ArrayList<Currency> currencyList = (ArrayList<Currency>) currencyDAO.getAll();
        SpinnerAdapter currencyAdapter = new SpinnerAdapter(this, TYPE_CURRENCY,
                                                                                currencyList);
        spinnerCurrency.setAdapter(currencyAdapter);
    }

    private void initStorageLocationSpinner(AppDatabase db) {
        StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
        storageLocationList = new ArrayList<>();
        storageLocationList.addAll(storageLocationDAO.getAll());
        storageLocationAdapter = new SpinnerAdapter(this, TYPE_STORAGE_LOCATION,
                                                                    storageLocationList);
        spinnerStorageLocation.setAdapter(storageLocationAdapter);
    }

    private void initButtons() {
        Button bAddCategory = findViewById(R.id.buttonAddCategory);
        bAddCategory.setOnClickListener(view -> addingDialog(TYPE_CATEGORY));

        if (type_adding == ADDING_REVENUE) {
            Button bAddStorageLocation = findViewById(R.id.buttonAddStorageLocation);
            bAddStorageLocation.setOnClickListener(view -> addingDialog(TYPE_STORAGE_LOCATION));
        }

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

        AppDatabase db        = App.getInstance().getAppDatabase();

        int    category       = ((Category)spinnerCategory.getSelectedItem()).getId();

        int date_day   = dateAndTime.get(Calendar.DAY_OF_MONTH);
        int date_month = dateAndTime.get(Calendar.MONTH) + 1;
        int date_year  = dateAndTime.get(Calendar.YEAR);

        String description    = editTextDescription.getText().toString();
        double value          = getValue();
        int    currency       = ((Currency)spinnerCurrency.getSelectedItem()).getId();

        if (type_adding == ADDING_REVENUE) {
            int storage_location = ((StorageLocation) spinnerStorageLocation.getSelectedItem())
                                                                            .getId();

            Revenues revenue      = new Revenues(value, currency, category, date_day,
                                                 date_month, date_year, description,
                                                 storage_location);
            RevenueDAO revenueDAO = db.revenueDAO();
            revenueDAO.insert(revenue);
        } else if (type_adding == ADDING_EXPENSES) {
            Expenses expenses       = new Expenses(value, currency, category, date_day,
                                                     date_month, date_year, description);
            ExpensesDAO expensesDAO = db.expensesDAO();
            expensesDAO.insert(expenses);
        }

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
        int maxLength        = 32;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0]            = new InputFilter.LengthFilter(maxLength);
        input.setFilters(fArray);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Добавить",
                (dialog, which) -> {
                    if (IsCorrectAdding(type, input.getText().toString())) {
                        addAddingVariable(type, input.getText().toString());
                        Toast.makeText(ctxRevenueAdd, "Успено добавлена",
                                Toast.LENGTH_LONG).show();
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
        Category new_category = new Category(type_adding, str);
        categoryDAO.insert(new_category);
        categoryList.add(new_category);
        categoryAdapter.notifyDataSetChanged();
    }

    private void insertNewStorageLocation(AppDatabase db, String str) {
        StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
        StorageLocation storageLocation = new StorageLocation(str);
        storageLocationDAO.insert(storageLocation);
        storageLocationList.add(storageLocation);
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

    private void setEditingData(Intent intent) {
        DecimalFormat precision = new DecimalFormat("0.00");
        editTextValue.setText(precision.format(intent.getDoubleExtra(VALUE_EDITING, 0)));
        editTextDescription.setText(intent.getStringExtra(DESC_EDITING));
    }

}