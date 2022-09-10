package com.snail.wallet.MainScreen.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snail.wallet.MainScreen.activities.AddActivity;
import com.snail.wallet.MainScreen.db.App;
import com.snail.wallet.MainScreen.db.AppDatabase;
import com.snail.wallet.MainScreen.db.CategoryDAO.CategoryDAO;
import com.snail.wallet.MainScreen.db.CurrencyDAO.CurrencyDAO;
import com.snail.wallet.MainScreen.models.money.Expenses;
import com.snail.wallet.MainScreen.models.money.Revenues;
import com.snail.wallet.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    /** List of revenues which display in recyclerView */
    private final List localData;
    /** Context */
    private final Context mContext;

    private int typeData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /** TextView description revenue  in list */
        private final TextView textViewDescription;
        /** TextView category revenue in list */
        private final TextView textViewCategory;
        /** TextView value revenue in list */
        private final TextView textViewValue;

        /** Constructor
         *
         * @param view View
         */
        public ViewHolder(View view) {
            super(view);
            textViewDescription = view.findViewById(R.id.textViewRecyclerViewDescription);
            textViewCategory    = view.findViewById(R.id.textViewCategoryRecyclerView);
            textViewValue       = view.findViewById(R.id.textViewValueRecyclerView);
        }
    }

    /**Constructor custom adapter
     *
     * @param data data list
     * @param context context
     */
    public RecyclerViewAdapter(int type, List data, Context context) {
        typeData  = type;
        localData = data;
        mContext  = context;
    }

    /**Create new views (invoked by the layout manager)
     *
     * @param viewGroup The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recyclerview, viewGroup, false);

        return new ViewHolder(view);
    }

    /**Replace the contents of a view (invoked by the layout manager)
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        AppDatabase db = App.getInstance().getAppDatabase();
        CategoryDAO categoryDAO = db.categoryDAO();
        CurrencyDAO currencyDAO = db.currencyDAO();

        if (typeData == AddActivity.ADDING_REVENUE) {
            Revenues revenues = (Revenues) localData.get(position);

            viewHolder.textViewDescription.setText(revenues.getDescription());
            viewHolder.textViewCategory.setText(categoryDAO.getCategoryById(revenues.getCategory()).getName());

            String val = revenues.getValue() + currencyDAO.getCurrencyById(revenues.getCurrency()).getSymbol();
            viewHolder.textViewValue.setText(val);

            viewHolder.itemView.setOnClickListener(view -> StartInfoActivity(position));
        } else if (typeData == AddActivity.ADDING_EXPENSES) {
            Expenses expenses = (Expenses) localData.get(position);

            viewHolder.textViewDescription.setText(expenses.getDescription());
            viewHolder.textViewCategory.setText(categoryDAO.getCategoryById(expenses.getCategory()).getName());

            String val = expenses.getValue() + currencyDAO.getCurrencyById(expenses.getCurrency()).getSymbol();
            viewHolder.textViewValue.setText(val);

            viewHolder.itemView.setOnClickListener(view -> StartInfoActivity(position));
        }
    }

    public void StartInfoActivity(int position) {
    }

    /**Method to get number of item's to display in recyclerView
     *
     * @return num of item's to display
     */
    @Override
    public int getItemCount() {
        return localData.size();
    }
}
