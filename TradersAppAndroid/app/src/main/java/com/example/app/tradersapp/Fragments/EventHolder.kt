package com.example.app.tradersapp.Fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.app.tradersapp.R


class EventHolder(inflater: LayoutInflater, parent:ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.event_recycler_item,parent,false)) {

    var eImage: ImageView? = null
    var eTitle: TextView? = null
    var eBody: TextView? = null

    init {
        eImage = itemView.findViewById(R.id.eImage)
        eTitle = itemView.findViewById(R.id.eTitle)
        eBody = itemView.findViewById(R.id.eBody)
    }

    fun bind(item: EventModel) {
        eBody?.text = item.body
        eTitle?.text = item.title
        eImage?.setImageResource(item.image)
        eImage?.tag = item.image
    }

}

data class EventModel(
     val image: Int,
     val title: String,
     val body: String
)
