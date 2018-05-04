package com.purpletealabs.bitcoinapp.adapters;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.purpletealabs.bitcoinapp.databinding.RowCountryBinding;
import com.purpletealabs.bitcoinapp.dtos.Currency;
import com.purpletealabs.bitcoinapp.viewmodels.CountryViewModel;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder> {
    private final ObservableArrayList<Currency> countries;
    private final ClickListener listener;

    public CountryListAdapter(ObservableArrayList<Currency> countries, ClickListener listener) {
        this.countries = countries;
        this.listener = listener;
        this.countries.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Currency>>() {
            @Override
            public void onChanged(ObservableList<Currency> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Currency> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<Currency> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<Currency> sender, int fromPosition, int toPosition, int itemCount) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Currency> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowCountryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Currency currency = countries.get(position);
        holder.binding.setVm(new CountryViewModel(currency.getCurrency(), currency.getCountry()));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RowCountryBinding binding;

        ViewHolder(RowCountryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCountrySelected(countries.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface ClickListener {
        void onCountrySelected(Currency currency);
    }
}