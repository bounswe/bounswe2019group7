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

    var eaImage: ImageView? = null
    var eaTitle: TextView? = null
    var eaBody: TextView? = null

    init {
        eaImage = itemView.findViewById(R.id.eaImage)
        eaTitle = itemView.findViewById(R.id.eaTitle)
        eaBody = itemView.findViewById(R.id.eaBody)
    }

    fun bind(item: EventModel) {
        eaBody?.text = item.body
        eaTitle?.text = item.title
        eaImage?.setImageResource(item.image)
        eaImage?.tag = item.image
    }

}

data class EventModel(
     val image: Int,
     val title: String,
     val body: String
)
