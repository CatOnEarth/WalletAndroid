package com.snail.wallet.MainScreen.ui.adapters;


import static com.snail.wallet.WalletConstants.CODE_TYPE_PARAM_CATEGORY;
import static com.snail.wallet.WalletConstants.CODE_TYPE_PARAM_CURRENCY;
import static com.snail.wallet.WalletConstants.CODE_TYPE_PARAM_STORAGE_LOCATION;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.snail.wallet.MainScreen.models.parametrs.Category;
import com.snail.wallet.MainScreen.models.parametrs.Currency;
import com.snail.wallet.MainScreen.models.parametrs.StorageLocation;
import com.snail.wallet.R;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter {
    private final String TAG = this.getClass().getSimpleName();

    private final int type_spinner;

    public SpinnerAdapter(Context context, int type, ArrayList items) {
        super(context, 0, items);
        Log.d(TAG, "SpinnerAdapter method with type_spinner = " + type);

        type_spinner = type;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        TextView textViewName;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner, parent, false);
            textViewName = convertView.findViewById(R.id.textViewItemSpinnerAdapter);
        } else {
            return convertView;
        }

        selectLayoutData(textViewName, position);

        return convertView;
    }

    private void selectLayoutData(TextView textViewName, int position) {
        Log.d(TAG, "selectLayoutData method");

        if        (type_spinner == CODE_TYPE_PARAM_CATEGORY) {
            setDataCategoryLayout(textViewName, position);
        } else if (type_spinner == CODE_TYPE_PARAM_CURRENCY) {
            setDataCurrencyLayout(textViewName, position);
        } else if (type_spinner == CODE_TYPE_PARAM_STORAGE_LOCATION) {
            setDataStorageLocationLayout(textViewName, position);
        }
    }

    private void setDataCategoryLayout(TextView textViewName, int position) {
        Log.d(TAG, "setDataCategoryLayout method");

        Category currentItem = (Category) getItem(position);
        if (currentItem != null) {
            textViewName.setText(currentItem.getName());
        }
    }

    private void setDataStorageLocationLayout(TextView textViewName, int position) {
        Log.d(TAG, "setDataStorageLocationLayout method");

        StorageLocation currentItem = (StorageLocation) getItem(position);
        if (currentItem != null) {
            textViewName.setText(currentItem.getLocation());
        }
    }

    private void setDataCurrencyLayout(TextView textViewName, int position) {
        Log.d(TAG, "setDataCurrencyLayout method");

        Currency currentItem = (Currency) getItem(position);

        if (currentItem != null) {
            textViewName.setText(currentItem.getSymbol());
        }
    }
}
