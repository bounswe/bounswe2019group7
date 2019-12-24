package com.example.app.tradersapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.app.tradersapp.Fragments.*


class FundsAdapter(private val list: List<FundsModel>, context: Context)
        : RecyclerView.Adapter<FundsHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FundsHolder {
            val inflater = LayoutInflater.from(parent.context)
            return FundsHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: FundsHolder, position: Int) {
            val fundsModel: FundsModel = list[position]
            holder.bind(fundsModel)
        }
        override fun getItemCount(): Int = list.size
    }

