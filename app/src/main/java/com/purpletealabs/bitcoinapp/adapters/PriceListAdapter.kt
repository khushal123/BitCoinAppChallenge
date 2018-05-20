package com.purpletealabs.bitcoinapp.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.purpletealabs.bitcoinapp.BR
import com.purpletealabs.bitcoinapp.beans.PriceData
import com.purpletealabs.bitcoinapp.databinding.RowPriceBinding
import com.purpletealabs.bitcoinapp.dtos.Price
import com.purpletealabs.bitcoinapp.viewmodels.PriceViewModel
import com.purpletealabs.bitcoinapp.R

class PriceListAdapter(internal var context: Context, private val priceList: ObservableArrayList<Price>) : RecyclerView.Adapter<PriceListAdapter.ViewHolder>() {
    init {
        this.priceList.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<Price>>() {
            override fun onChanged(sender: ObservableList<Price>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(sender: ObservableList<Price>, positionStart: Int, itemCount: Int) {
                notifyItemRangeChanged(positionStart, itemCount)
            }

            override fun onItemRangeInserted(sender: ObservableList<Price>, positionStart: Int, itemCount: Int) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeMoved(sender: ObservableList<Price>, fromPosition: Int, toPosition: Int, itemCount: Int) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(sender: ObservableList<Price>, positionStart: Int, itemCount: Int) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_price, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = priceList[position]
        val filename = "_" + p.code?.toLowerCase()
        val resID = context.resources.getIdentifier(filename, "drawable", context.packageName)
        p.drawable = resID
        holder.binding?.price = p
        holder.binding?.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return priceList.size
    }

      class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding: RowPriceBinding? = DataBindingUtil.bind(view)
    }
}