package com.purpletealabs.bitcoinapp.adapters;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.purpletealabs.bitcoinapp.databinding.RowPriceBinding;
import com.purpletealabs.bitcoinapp.dtos.Price;
import com.purpletealabs.bitcoinapp.viewmodels.PriceViewModel;

public class PriceListAdapter extends RecyclerView.Adapter<PriceListAdapter.ViewHolder> {
    private final ObservableArrayList<Price> priceList;

    public PriceListAdapter(ObservableArrayList<Price> priceList) {
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
        return new ViewHolder(RowPriceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Price p = priceList.get(position);
        holder.binding.setVm(new PriceViewModel(String.format("%s %s", p.getCode(), p.getRate())));
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RowPriceBinding binding;

        ViewHolder(RowPriceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}