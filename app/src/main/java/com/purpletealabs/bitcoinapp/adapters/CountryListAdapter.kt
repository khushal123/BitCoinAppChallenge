package com.purpletealabs.bitcoinapp.adapters

import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.purpletealabs.bitcoinapp.databinding.RowCountryBinding
import com.purpletealabs.bitcoinapp.dtos.Currency
import com.purpletealabs.bitcoinapp.viewmodels.CountryViewModel

class CountryListAdapter(internal var context: Context, private val countries: ObservableArrayList<Currency>, private val listener: ClickListener) : RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {
    init {
        this.countries.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<Currency>>() {
            override fun onChanged(sender: ObservableList<Currency>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(sender: ObservableList<Currency>, positionStart: Int, itemCount: Int) {
                notifyItemRangeChanged(positionStart, itemCount)
            }

            override fun onItemRangeInserted(sender: ObservableList<Currency>, positionStart: Int, itemCount: Int) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeMoved(sender: ObservableList<Currency>, fromPosition: Int, toPosition: Int, itemCount: Int) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(sender: ObservableList<Currency>, positionStart: Int, itemCount: Int) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = countries[position]
        val filename = "_" + currency.currency?.toLowerCase()
        val resID = context.resources.getIdentifier(filename, "drawable", context.packageName)
        holder.binding.vm = CountryViewModel(currency.currency, currency.country, resID)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return countries.size
    }

     inner class ViewHolder(var binding: RowCountryBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            this.itemView.setOnClickListener { listener.onCountrySelected(countries[adapterPosition]) }
        }
    }

    interface ClickListener {
        fun onCountrySelected(currency: Currency)
    }
}