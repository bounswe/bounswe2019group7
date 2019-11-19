package com.example.app.tradersapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

/* This class is not used now!!! Will be deleted if it is decided that it is unnecessary!*/
class CurrenciesAdapter(private val data: MutableList<CurrencyEntry>, context: Context) : RecyclerView.Adapter<CurrenciesAdapter.CurrencyEntryHolder>() {

    val mContext = context

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyEntryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false)
        return CurrencyEntryHolder(view) // Create your ViewHolder here
    }

    override fun onBindViewHolder(holder: CurrencyEntryHolder, position: Int) {
        val currencyEntry : CurrencyEntry = data[position]
        holder.tv.text = currencyEntry.currency
        holder.flag.setImageResource(currencyEntry.flagId)
        holder.followButton.setOnClickListener {
            Toast.makeText(mContext, "Follow button is clicked for " + currencyEntry.currency, Toast.LENGTH_SHORT).show();
        }

        holder.itemView.setOnClickListener {
            // TODO:  plot fragment from here.
        }

    }

    override fun getItemCount() = data.size


    class CurrencyEntryHolder(view: View) : RecyclerView.ViewHolder(view){
        val tv: TextView = view.findViewById(R.id.tv)
        val flag: ImageView = view.findViewById(R.id.flag)
        val followButton: Button = view.findViewById(R.id.followButton)

    }


}
data class CurrencyEntry(val currency: String, val flagId: Int)