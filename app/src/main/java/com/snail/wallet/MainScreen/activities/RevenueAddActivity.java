package com.snail.wallet.MainScreen.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.snail.wallet.MainScreen.WalletActivity;
import com.snail.wallet.MainScreen.models.Category;
import com.snail.wallet.MainScreen.models.Currency;
import com.snail.wallet.MainScreen.models.MoneySource;
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

    TextView date;
    Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_add);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        } else {
            Log.e(TAG, "ActionBar was null");
            finish();
        }

        spinnerCategory        = findViewById(R.id.spinnerCategoryRevenue);
        spinnerSource          = findViewById(R.id.spinnerSourceRevenue);
        spinnerCurrency        = findViewById(R.id.spinnerCurrencyRevenue);
        spinnerStorageLocation = findViewById(R.id.spinnerStorageLocationRevenue);

        ArrayList<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, 2, "первая"));
        categoryList.add(new Category(2, 3, "вторая"));
        categoryAdapter = new CategoryAdapter(this, categoryList);
        spinnerCategory.setAdapter(categoryAdapter);

        ArrayList<MoneySource> sourceList = new ArrayList<>();
        sourceList.add(new MoneySource(1, "работа"));
        sourceList.add(new MoneySource(2, "акции"));
        sourceAdapter = new SourceAdapter(this, sourceList);
        spinnerSource.setAdapter(sourceAdapter);

        ArrayList<Currency> currencyList = new ArrayList<>();
        currencyList.add(new Currency(1, "Российский рубль", "первая"));
        currencyList.add(new Currency(2, "Евро", "вторая"));
        currencyAdapter = new CurrencyAdapter(this, currencyList);
        spinnerCurrency.setAdapter(currencyAdapter);

        ArrayList<StorageLocation> storageLocationList = new ArrayList<>();
        storageLocationList.add(new StorageLocation(1, "банк"));
        storageLocationList.add(new StorageLocation(2, "сейф"));
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
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
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
            return (true);
        }

        return(super.onOptionsItemSelected(item));
    }
}