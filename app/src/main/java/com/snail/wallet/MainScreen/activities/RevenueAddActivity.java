package com.snail.wallet.MainScreen.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.snail.wallet.MainScreen.WalletActivity;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.CategoryDAO.CategoryDAO;
import com.snail.wallet.MainScreen.db.CurrencyDAO.CurrencyDAO;
import com.snail.wallet.MainScreen.db.MoneySouceDAO.MoneySourceDAO;
import com.snail.wallet.MainScreen.db.RevenueDAO.RevenueDAO;
import com.snail.wallet.MainScreen.db.StorageLocationDAO.StorageLocationDAO;
import com.snail.wallet.MainScreen.models.Category;
import com.snail.wallet.MainScreen.models.Currency;
import com.snail.wallet.MainScreen.models.MoneySource;
import com.snail.wallet.MainScreen.models.Revenues;
import com.snail.wallet.MainScreen.models.StorageLocation;
import com.snail.wallet.MainScreen.ui.adapters.CategoryAdapter;
import com.snail.wallet.MainScreen.ui.adapters.CurrencyAdapter;
import com.snail.wallet.MainScreen.ui.adapters.SourceAdapter;
import com.snail.wallet.MainScreen.ui.adapters.StorageLocationAdapter;
import com.snail.wallet.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RevenueAddActivity extends AppCompatActivity {
    /**  */
    private final String TAG = this.getClass().getSimpleName();

    /**  */
    private Spinner spinnerSource;
    private Spinner spinnerCategory;
    private Spinner spinnerCurrency;
    private Spinner spinnerStorageLocation;

    private SourceAdapter   sourceAdapter;
    private CategoryAdapter categoryAdapter;
    private CurrencyAdapter currencyAdapter;
    private StorageLocationAdapter storageLocationAdapter;

    private EditText editTextValue;
    private EditText editTextDescription;

    private TextView date;
    private Calendar dateAndTime = Calendar.getInstance();

    private Context ctxRevenueAdd;

    private ArrayList<Category> categoryList;
    private ArrayList<MoneySource> sourceList;
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

        editTextValue = findViewById(R.id.editTextNumberValueRevenue);
        editTextDescription = findViewById(R.id.editTextTextDescriptionRevenue);

        spinnerCategory        = findViewById(R.id.spinnerCategoryRevenue);
        spinnerSource          = findViewById(R.id.spinnerSourceRevenue);
        spinnerCurrency        = findViewById(R.id.spinnerCurrencyRevenue);
        spinnerStorageLocation = findViewById(R.id.spinnerStorageLocationRevenue);

        AppDatabase db          = App.getInstance().getAppDatabase();

        CategoryDAO categoryDAO = db.categoryDAO();
        categoryList = new ArrayList<Category>();
        categoryList = (ArrayList<Category>) categoryDAO.getAll();
        categoryAdapter = new CategoryAdapter(this, categoryList);
        spinnerCategory.setAdapter(categoryAdapter);

        MoneySourceDAO moneySourceDAO = db.moneySourceDAO();
        sourceList = new ArrayList<MoneySource>();
        sourceList.addAll((ArrayList<MoneySource>) moneySourceDAO.getAll());
        sourceAdapter = new SourceAdapter(this, sourceList);
        spinnerSource.setAdapter(sourceAdapter);

        CurrencyDAO currencyDAO = db.currencyDAO();
        ArrayList<Currency> currencyList = (ArrayList<Currency>) currencyDAO.getAll();
        currencyAdapter = new CurrencyAdapter(this, currencyList);
        spinnerCurrency.setAdapter(currencyAdapter);

        StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
        storageLocationList = new ArrayList<StorageLocation>();
        storageLocationList.addAll((ArrayList<StorageLocation>) storageLocationDAO.getAll());
        storageLocationAdapter = new StorageLocationAdapter(this, storageLocationList);
        spinnerStorageLocation.setAdapter(storageLocationAdapter);

        date = findViewById(R.id.textViewDateSelectedRevenue);
        setInitialDateTime();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(view);
            }
        });

        Button bAddSource = findViewById(R.id.buttonAddSource);
        bAddSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingDialog( 0);
            }
        });

        Button bAddCategory = findViewById(R.id.buttonAddCategory);
        bAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingDialog(1);
            }
        });

        Button bAddStorageLocation = findViewById(R.id.buttonAddStorageLocation);
        bAddStorageLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingDialog(2);
            }
        });


        Button bSaveRevenue = findViewById(R.id.bAddRevenueSave);
        bSaveRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveRevenue();
            }
        });
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {
        date.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(RevenueAddActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        Log.i(TAG, "Start onCreateOptionsMenu method LoginActivity");
        getMenuInflater().inflate(R.menu.add_revenue_menu, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "Start onOptionsItemSelected method");
        if (item.getItemId() == R.id.save) {
            Log.i(TAG, "save choose in menu actionBar");
            SaveRevenue();
        }

        return(super.onOptionsItemSelected(item));
    }

    private void SaveRevenue() {
        if (!IsAllCorrect()) {
            return;
        }

        int    category         = ((Category)spinnerCategory.getSelectedItem()).getId();
        String dateStr          = date.toString();
        String description      = editTextDescription.getText().toString();
        double value            = getValue();
        int    currency         = ((Currency)spinnerCurrency.getSelectedItem()).getId();
        int    storage_location = ((StorageLocation)spinnerStorageLocation.getSelectedItem()).getId();
        int    source           = ((MoneySource)spinnerSource.getSelectedItem()).getId();

        Revenues revenue = new Revenues(value, currency, category, dateStr, description, storage_location, source);

        AppDatabase db          = App.getInstance().getAppDatabase();
        RevenueDAO revenueDAO = db.revenueDAO();

        revenueDAO.insert(revenue);

        finish();
    }

    private boolean IsAllCorrect() {
        if (IsCorrectValue()) {
            return true;
        }
        return false;
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

    private void addingDialog(int type) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RevenueAddActivity.this);
        if (type == 0) {
            alertDialog.setTitle("Источник");
            alertDialog.setMessage("Добавить источник");
        } else if (type == 1) {
            alertDialog.setTitle("Категория");
            alertDialog.setMessage("Добавить категорию");
        } else if (type == 2) {
            alertDialog.setTitle("Место хранения");
            alertDialog.setMessage("Добавить место хранения");
        } else {
            return;
        }


        final EditText input = new EditText(RevenueAddActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("Добавить",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (IsCorrectAdding(type, input.getText().toString())) {
                            addAddingVariable(type, input.getText().toString());
                            Toast.makeText(ctxRevenueAdd, "Успено добавлена", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        alertDialog.setNegativeButton("Отмена",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    private void addAddingVariable(int type, String str) {
        AppDatabase db = App.getInstance().getAppDatabase();

        if (type == 0) {
            MoneySourceDAO moneySourceDAO = db.moneySourceDAO();
            moneySourceDAO.insert(new MoneySource(str));
            sourceList.clear();
            sourceList.addAll((ArrayList<MoneySource>) moneySourceDAO.getAll());
            sourceAdapter.notifyDataSetChanged();
        } else if (type == 1) {
            CategoryDAO categoryDAO = db.categoryDAO();
            categoryDAO.insert(new Category(1, str));
            categoryList.clear();
            categoryList.addAll((ArrayList<Category>) categoryDAO.getAll());
            categoryAdapter.notifyDataSetChanged();
        } else if (type == 2) {
            StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
            storageLocationDAO.insert(new StorageLocation(str));
            storageLocationList.clear();
            storageLocationList.addAll((ArrayList<StorageLocation>) storageLocationDAO.getAll());
            storageLocationAdapter.notifyDataSetChanged();
        }
    }

    private boolean IsCorrectAdding(int type, String str) {
        if (str.length() == 0) {
            return false;
        }

        AppDatabase db = App.getInstance().getAppDatabase();

        if (type == 0) {
            MoneySourceDAO moneySourceDAO = db.moneySourceDAO();
            return moneySourceDAO.getBySourceName(str).size() == 0;
        } else if (type == 1) {
            CategoryDAO categoryDAO = db.categoryDAO();
            return categoryDAO.getCategoryName(str).size() == 0;
        } else if (type == 2) {
            StorageLocationDAO storageLocationDAO = db.storageLocationDAO();
            return storageLocationDAO.getByStorageLocationName(str).size() == 0;
        } else {
            return false;
        }
    }
}