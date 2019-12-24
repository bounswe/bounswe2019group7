package com.example.app.tradersapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.app.tradersapp.R


class FundsHolder(inflater: LayoutInflater, parent:ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.funds_recycler_item,parent,false)) {

    var fundsName: TextView? = null
    var fundsAmount: TextView? = null
    init {
        fundsName = itemView.findViewById(R.id.fundsName)
        fundsAmount = itemView.findViewById(R.id.fundsAmount)
    }

    fun bind(item: FundsModel) {
        fundsName?.text = item.name
        fundsAmount?.text = item.amount.toString()
    }

}

data class FundsModel(
     val name: String,
     val amount: Double
)
