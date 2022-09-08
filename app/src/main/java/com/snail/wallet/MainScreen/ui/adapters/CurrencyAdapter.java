package com.snail.wallet.MainScreen.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.snail.wallet.MainScreen.models.Category;
import com.snail.wallet.MainScreen.models.Currency;
import com.snail.wallet.R;

import java.util.ArrayList;

public class CurrencyAdapter extends ArrayAdapter<Currency> {
    public CurrencyAdapter(Context context, ArrayList<Currency> currency) {
        super(context, 0, currency);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_currency_spinner, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.textViewCurrencySpinnerRevenueAdapter);
        Currency currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getName());
        }
        return convertView;
    }
}
