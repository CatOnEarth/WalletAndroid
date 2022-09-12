package com.snail.wallet.MainScreen.ui.adapters;


import static com.snail.wallet.WalletConstants.ADDING_OBJECT_TYPE;
import static com.snail.wallet.WalletConstants.ADDING_OBJ_EXPENSES_TYPE;
import static com.snail.wallet.WalletConstants.ADDING_OBJ_REVENUE_TYPE;
import static com.snail.wallet.WalletConstants.ID_SHOW_OBJ;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snail.wallet.MainScreen.activities.ShowActivity;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.CategoryDAO.CategoryDAO;
import com.snail.wallet.MainScreen.db.CurrencyDAO.CurrencyDAO;
import com.snail.wallet.MainScreen.models.money.Expenses;
import com.snail.wallet.MainScreen.models.money.Revenues;
import com.snail.wallet.R;

import java.text.DecimalFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private final String TAG = this.getClass().getSimpleName();

    private final List    localData;
    private final Context mContext;
    private final int     typeData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewDescription;
        private final TextView textViewCategory;
        private final TextView textViewValue;

        public ViewHolder(View view) {
            super(view);
            textViewDescription = view.findViewById(R.id.textViewRecyclerViewDescription);
            textViewCategory    = view.findViewById(R.id.textViewCategoryRecyclerView);
            textViewValue       = view.findViewById(R.id.textViewValueRecyclerView);
        }
    }

    public RecyclerViewAdapter(int type, List data, Context context) {
        Log.d(TAG, "RecyclerViewAdapter method");

        typeData  = type;
        localData = data;
        mContext  = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recyclerview, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder method");

        AppDatabase db = App.getInstance().getAppDatabase();
        CategoryDAO categoryDAO = db.categoryDAO();
        CurrencyDAO currencyDAO = db.currencyDAO();

        DecimalFormat precision = new DecimalFormat("0.00");

        if (typeData == ADDING_OBJ_REVENUE_TYPE) {
            initRevenueAddObj(viewHolder, precision, categoryDAO, currencyDAO,
                                                                    localData.get(position));
        } else if (typeData == ADDING_OBJ_EXPENSES_TYPE) {
            initExpensesAddObj(viewHolder, precision, categoryDAO, currencyDAO,
                                                                    localData.get(position));
        }
    }

    private void initExpensesAddObj(ViewHolder viewHolder, DecimalFormat precision,
                                    CategoryDAO categoryDAO, CurrencyDAO currencyDAO, Object obj) {

        Log.d(TAG, "initExpensesAddObj method");

        Expenses expenses = (Expenses) obj;

        viewHolder.textViewDescription.setText(expenses.getDescription());
        viewHolder.textViewCategory.setText(categoryDAO.getCategoryById(expenses.getId_category())
                                                                                .getName());
        String val = precision.format(expenses.getValue()) + currencyDAO.getCurrencyByType(expenses
                                                                .getType_currency()).getSymbol();
        viewHolder.textViewValue.setText(val);
        viewHolder.itemView.setOnClickListener(view -> StartInfoActivity(expenses.getId()));
    }

    private void initRevenueAddObj(ViewHolder viewHolder, DecimalFormat precision,
                                   CategoryDAO categoryDAO, CurrencyDAO currencyDAO, Object obj) {

        Log.d(TAG, "initRevenueAddObj method");

        Revenues revenues = (Revenues) obj;

        viewHolder.textViewDescription.setText(revenues.getDescription());
        viewHolder.textViewCategory.setText(categoryDAO.getCategoryById(revenues.getId_category())
                                                                                .getName());
        String val = precision.format(revenues.getValue()) + currencyDAO.getCurrencyByType(revenues
                                                                .getType_currency()).getSymbol();
        viewHolder.textViewValue.setText(val);
        viewHolder.itemView.setOnClickListener(view -> StartInfoActivity(revenues.getId()));
    }

    public void StartInfoActivity(long id) {
        Log.d(TAG, "StartInfoActivity method");

        Intent intent = new Intent(mContext, ShowActivity.class);
        intent.putExtra(ADDING_OBJECT_TYPE, typeData);
        intent.putExtra(ID_SHOW_OBJ, id);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return localData.size();
    }
}
