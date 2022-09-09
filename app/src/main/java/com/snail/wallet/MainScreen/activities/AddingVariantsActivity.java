package com.snail.wallet.MainScreen.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.snail.wallet.R;

public class AddingVariantsActivity extends AppCompatActivity {

    public static final String TYPE_ADDING = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int type_add = intent.getIntExtra(TYPE_ADDING, -1);

        if (type_add == 0) { // currency

        } else if (type_add == 1) { //source

        } else if (type_add == 2) { // category
            setContentView(R.layout.activity_adding_category);

        } else if (type_add == 3) { // storage location

        } else {
            finish();
        }



    }
}