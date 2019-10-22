package com.example.app.tradersapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.app.tradersapp.Fragments.EventArticleHolder
import com.example.app.tradersapp.Fragments.EventArticleModel


class EventArticleAdapter(private val list: List<EventArticleModel>)
        : RecyclerView.Adapter<EventArticleHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventArticleHolder {
            val inflater = LayoutInflater.from(parent.context)
            return EventArticleHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: EventArticleHolder, position: Int) {
            val eaModel: EventArticleModel = list[position]
            holder.bind(eaModel)
        }

        override fun getItemCount(): Int = list.size

    }

