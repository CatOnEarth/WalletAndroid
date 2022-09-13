package com.snail.wallet.MainScreen.activities;

import static com.snail.wallet.WalletConstants.ADDING_OBJECT_TYPE;
import static com.snail.wallet.WalletConstants.ADDING_OBJ_EXPENSES_TYPE;
import static com.snail.wallet.WalletConstants.ADDING_OBJ_REVENUE_TYPE;
import static com.snail.wallet.WalletConstants.CATEGORY_EDITING_OBJ;
import static com.snail.wallet.WalletConstants.CODE_TYPE_PARAM_CATEGORY;
import static com.snail.wallet.WalletConstants.CODE_TYPE_PARAM_CURRENCY;
import static com.snail.wallet.WalletConstants.CODE_TYPE_PARAM_STORAGE_LOCATION;
import static com.snail.wallet.WalletConstants.CURRENCY_EDITING_OBJ;
import static com.snail.wallet.WalletConstants.DATE_DAY_EDITING_OBJ;
import static com.snail.wallet.WalletConstants.DATE_MONTH_EDITING_OBJ;
import static com.snail.wallet.WalletConstants.DATE_YEAR_EDITING_OBJ;
import static com.snail.wallet.WalletConstants.DESC_EDITING_OBJ;
import static com.snail.wallet.WalletConstants.ID_EDITING_OBJ;
import static com.snail.wallet.WalletConstants.STORAGE_LOCATION_EDITING_OBJ;
import static com.snail.wallet.WalletConstants.VALUE_EDITING_OBJ;

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

    private Spinner spinnerCategory;
    private Spinner spinnerCurrency;
    private Spinner spinnerStorageLocation;

    private SpinnerAdapter categoryAdapter;
    private SpinnerAdapter currencyAdapter;
    private SpinnerAdapter storageLocationAdapter;

    private EditText editTextValue;
    private EditText editTextDescription;

    private TextView       date;
    private final Calendar dateAndTime = Calendar.getInstance();
    private boolean firstSetDate       = false;

    private Context ctxRevenueAdd;

    private ArrayList<Category>        categoryList;
    private ArrayList<StorageLocation> storageLocationList;

    private long id_editing;

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
        type_adding   = intent.getIntExtra(ADDING_OBJECT_TYPE, -1);
        id_editing    = intent.getLongExtra(ID_EDITING_OBJ, -1);
        if (type_adding == -1) {
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

        if (type_adding == ADDING_OBJ_REVENUE_TYPE) {
            initStorageLocationSpinner(db);
        }
    }

    private void initFindView() {
        editTextValue       = findViewById(R.id.editTextNumberAddActivityValue);
        editTextDescription = findViewById(R.id.editTextAddActivityTextDescription);

        spinnerCategory        = findViewById(R.id.spinnerAddActivityCategory);
        spinnerCurrency        = findViewById(R.id.spinnerAddActivityCurrency);
        spinnerStorageLocation = findViewById(R.id.spinnerAddActivityStorageLocation);

        if (type_adding == ADDING_OBJ_EXPENSES_TYPE) {
            TextView textSpinnerStorageLocation = findViewById(R.id.textViewAddActivityStorageLocation);
            Button bAddStorageLocation = findViewById(R.id.buttonAddActivityAddStorageLocation);
            spinnerStorageLocation.setVisibility(View.INVISIBLE);
            textSpinnerStorageLocation.setVisibility(View.INVISIBLE);
            bAddStorageLocation.setVisibility(View.INVISIBLE);
        }

        date                   = findViewById(R.id.textViewAddActivityDateSelector);
    }

    private void initCategorySpinner(AppDatabase db) {
        CategoryDAO categoryDAO = db.categoryDAO();
        categoryList = new ArrayList<>();
        categoryList = (ArrayList<Category>) categoryDAO.getCategoryByType(type_adding);
        categoryAdapter = new SpinnerAdapter( this, CODE_TYPE_PARAM_CATEGORY, categoryList);
        spinnerCategory.setAdapter(categoryAdapter);
    }

    private void initCurrencySpinner(AppDatabase db) {
        CurrencyDAO currencyDAO = db.currencyDAO();
        ArrayList<Currency> currencyList = (ArrayList<Currency>) currencyDAO.getAll();
        currencyAdapter = new SpinnerAdapter(this, CODE_TYPE_PARAM_CURRENCY,
                                                                                currencyList);
        spinnerCurrency.setAdapter(currencyAdapter);
    }

    private void initStorageLocationSpinner(AppDatabase db) {
        StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
        storageLocationList = new ArrayList<>();
        storageLocationList.addAll(storageLocationDAO.getAll());
        storageLocationAdapter = new SpinnerAdapter(this, CODE_TYPE_PARAM_STORAGE_LOCATION,
                                                                    storageLocationList);
        spinnerStorageLocation.setAdapter(storageLocationAdapter);
    }

    private void initButtons() {
        Button bAddCategory = findViewById(R.id.buttonAddActivityAddCategory);
        bAddCategory.setOnClickListener(view -> addingDialog(CODE_TYPE_PARAM_CATEGORY));

        if (type_adding == ADDING_OBJ_REVENUE_TYPE) {
            Button bAddStorageLocation = findViewById(R.id.buttonAddActivityAddStorageLocation);
            bAddStorageLocation.setOnClickListener(view -> addingDialog(CODE_TYPE_PARAM_STORAGE_LOCATION));
        }

        Button bSaveRevenue = findViewById(R.id.buttonAddActivitySave);
        bSaveRevenue.setOnClickListener(view -> SaveElem());

        date.setOnClickListener(this::setDate);
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
        if (id_editing != -1 && !firstSetDate) {
            Intent intent = getIntent();
            dateAndTime.set(intent.getIntExtra(DATE_YEAR_EDITING_OBJ, 1),
                    intent.getIntExtra(DATE_MONTH_EDITING_OBJ, 1),
                    intent.getIntExtra(DATE_DAY_EDITING_OBJ, 1));
            firstSetDate = true;
        }

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
            SaveElem();
        } else if (item.getItemId() == android.R.id.home) {
            exitDialog();
        }

        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        exitDialog();
    }

    private void SaveElem() {
        if (!IsAllCorrect()) return;

        AppDatabase db        = App.getInstance().getAppDatabase();

        long    category       = ((Category)spinnerCategory.getSelectedItem()).getId();

        int date_day   = dateAndTime.get(Calendar.DAY_OF_MONTH);
        int date_month = dateAndTime.get(Calendar.MONTH);
        int date_year  = dateAndTime.get(Calendar.YEAR);

        String description    = editTextDescription.getText().toString();
        double value          = getValue();
        int    currency       = ((Currency)spinnerCurrency.getSelectedItem()).getType_currency();

        if (type_adding == ADDING_OBJ_REVENUE_TYPE) {
            long storage_location = ((StorageLocation) spinnerStorageLocation.getSelectedItem())
                                                                            .getId();

            Revenues revenue      = new Revenues(value, currency, category, date_day,
                                                 date_month, date_year, description,
                                                 storage_location);
            RevenueDAO revenueDAO = db.revenueDAO();

            if (id_editing == -1) {
                revenueDAO.insert(revenue);
            } else {
                revenue.setId(id_editing);
                revenueDAO.update(revenue);
            }
        } else if (type_adding == ADDING_OBJ_EXPENSES_TYPE) {
            Expenses expenses       = new Expenses(value, currency, category, date_day,
                                                     date_month, date_year, description);
            ExpensesDAO expensesDAO = db.expensesDAO();

            if (id_editing == -1) {
                expensesDAO.insert(expenses);
            } else {
                expenses.setId(id_editing);
                expensesDAO.update(expenses);
            }
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
                (dialog, which) -> SaveElem());

        alertDialog.setNegativeButton("Нет",
                (dialog, which) -> {
                    dialog.cancel();
                    finish();
                });

        alertDialog.show();
    }

    private void addingDialog(int type) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddActivity.this);
        if        (type == CODE_TYPE_PARAM_CATEGORY) {
            alertDialog.setTitle("Категория");
            alertDialog.setMessage("Добавить категорию");
        } else if (type == CODE_TYPE_PARAM_STORAGE_LOCATION) {
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

        if        (type == CODE_TYPE_PARAM_CATEGORY) {
            insertNewCategory(db, str);
        } else if (type == CODE_TYPE_PARAM_STORAGE_LOCATION) {
            insertNewStorageLocation(db, str);
        }
    }

    private void insertNewCategory(AppDatabase db, String str) {
        CategoryDAO categoryDAO = db.categoryDAO();
        Category new_category = new Category(type_adding, str);
        long new_category_id = categoryDAO.insert(new_category);
        new_category.setId(new_category_id);
        categoryList.add(new_category);
        categoryAdapter.notifyDataSetChanged();
    }

    private void insertNewStorageLocation(AppDatabase db, String str) {
        StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
        StorageLocation new_storageLocation = new StorageLocation(str);
        long new_storageLocation_id = storageLocationDAO.insert(new_storageLocation);
        new_storageLocation.setId(new_storageLocation_id);
        storageLocationList.add(new_storageLocation);
        storageLocationAdapter.notifyDataSetChanged();
    }

    private boolean IsCorrectAdding(int type, String str) {
        if (str.length() == 0) return false;

        AppDatabase db = App.getInstance().getAppDatabase();

         if       (type == CODE_TYPE_PARAM_CATEGORY) {
            return db.categoryDAO().getCategoryName(str).size() == 0;
        } else if (type == CODE_TYPE_PARAM_STORAGE_LOCATION) {
            return db.storageLocationDAO().getByStorageLocationName(str).size() == 0;
        } else {
            return false;
        }
    }

    private void setEditingData(Intent intent) {
        DecimalFormat precision = new DecimalFormat("0.00");
        editTextValue.setText(precision.format(intent.getDoubleExtra(VALUE_EDITING_OBJ, 0)));
        editTextDescription.setText(intent.getStringExtra(DESC_EDITING_OBJ));

        long category_id = intent.getLongExtra(CATEGORY_EDITING_OBJ, 0);
        for (int ii = 0; ii < categoryAdapter.getCount(); ++ii) {
            if (category_id == ((Category) categoryAdapter.getItem(ii)).getId()) {
                spinnerCategory.setSelection(ii);
            }
        }

        int currency_id = intent.getIntExtra(CURRENCY_EDITING_OBJ, 0);
        for (int ii = 0; ii < currencyAdapter.getCount(); ++ii) {
            if (currency_id == ((Currency) currencyAdapter.getItem(ii)).getId()) {
                spinnerCurrency.setSelection(ii);
            }
        }
        if (type_adding == ADDING_OBJ_REVENUE_TYPE) {
            int storage_location_id = intent.getIntExtra(STORAGE_LOCATION_EDITING_OBJ, 0);
            for (int ii = 0; ii < storageLocationAdapter.getCount(); ++ii) {
                if (storage_location_id == ((StorageLocation) storageLocationAdapter.getItem(ii)).getId()) {
                    spinnerStorageLocation.setSelection(ii);
                }
            }
        }

    }

}