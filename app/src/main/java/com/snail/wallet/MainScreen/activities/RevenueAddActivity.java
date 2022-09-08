package com.snail.wallet.MainScreen.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.snail.wallet.MainScreen.models.Category;
import com.snail.wallet.MainScreen.ui.adapters.CategoryAdapter;
import com.snail.wallet.R;

import java.util.ArrayList;
import java.util.List;

public class RevenueAddActivity extends AppCompatActivity {
    /**  */
    private final String TAG = this.getClass().getSimpleName();

    /**  */
    private Spinner spinnerSource;
    private CategoryAdapter category_adapter;

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

        spinnerSource = findViewById(R.id.spinnerSource);
        ArrayList<Category> arrayList = new ArrayList<>();

        arrayList.add(new Category(1, 2, "первая"));
        arrayList.add(new Category(2, 3, "вторая"));
        // we pass our item list and context to our Adapter.
        category_adapter = new CategoryAdapter(this, arrayList);
        spinnerSource.setAdapter(category_adapter);


    }

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