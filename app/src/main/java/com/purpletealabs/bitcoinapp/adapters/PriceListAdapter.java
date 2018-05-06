package com.purpletealabs.bitcoinapp.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.purpletealabs.bitcoinapp.BR;
import com.purpletealabs.bitcoinapp.beans.PriceData;
import com.purpletealabs.bitcoinapp.databinding.RowPriceBinding;
import com.purpletealabs.bitcoinapp.dtos.Price;
import com.purpletealabs.bitcoinapp.viewmodels.PriceViewModel;
import com.purpletealabs.bitcoinapp.R;

public class PriceListAdapter extends RecyclerView.Adapter<PriceListAdapter.ViewHolder> {
    private final ObservableArrayList<Price> priceList;

    Context context;
    public PriceListAdapter(Context context, ObservableArrayList<Price> priceList) {
        this.context = context;
        this.priceList = priceList;
        this.priceList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Price>>() {
            @Override
            public void onChanged(ObservableList<Price> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Price> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<Price> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<Price> sender, int fromPosition, int toPosition, int itemCount) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Price> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_price, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Price p = priceList.get(position);
        String filename = "_"+p.getCode().toLowerCase();
        int resID = context.getResources().getIdentifier(filename , "drawable", context.getPackageName());
        p.setDrawable(resID);
        holder.getBinding().setPrice(p);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
       RowPriceBinding binding;

        public ViewHolder(View view) {
            super(view);
            this.binding = DataBindingUtil.bind(view);
        }

        public RowPriceBinding getBinding() {
            return binding;
        }
    }
}