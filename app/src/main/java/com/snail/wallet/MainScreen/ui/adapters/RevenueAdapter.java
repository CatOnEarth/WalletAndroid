package com.snail.wallet.MainScreen.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snail.wallet.MainScreen.models.Revenues;
import com.snail.wallet.R;

import java.util.ArrayList;
import java.util.List;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.ViewHolder>{
    /** List of revenues which display in recyclerView */
    private final List<Revenues> localRevenues;
    /** Context */
    private final Context mContext;

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
     * @param revenues revenues list
     * @param context context
     */
    public RevenueAdapter(List<Revenues> revenues, Context context) {
        localRevenues = revenues;
        mContext      = context;
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
        Revenues revenues = localRevenues.get(position);

        viewHolder.textViewDescription.setText(revenues.getDescription());
        viewHolder.textViewCategory.setText(String.valueOf(revenues.getCategory()));

        String val = String.valueOf(revenues.getValue()) + revenues.getCurrencyType();
        viewHolder.textViewValue.setText(val);

        viewHolder.itemView.setOnClickListener(view -> StartInfoActivity(position));
    }

    public void StartInfoActivity(int position) {
    }

    /**Method to get number of item's to display in recyclerView
     *
     * @return num of item's to display
     */
    @Override
    public int getItemCount() {
        return localRevenues.size();
    }
}
