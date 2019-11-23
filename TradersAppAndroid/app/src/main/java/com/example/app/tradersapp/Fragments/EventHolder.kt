package com.example.app.tradersapp.Fragments

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.app.tradersapp.R
import kotlinx.android.synthetic.main.article_fragment.view.*


class EventHolder(inflater: LayoutInflater, parent:ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.event_fragment,parent,false)) {

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
