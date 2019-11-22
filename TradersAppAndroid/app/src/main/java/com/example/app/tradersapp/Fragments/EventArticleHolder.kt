package com.example.app.tradersapp.Fragments

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.app.tradersapp.R
import kotlinx.android.synthetic.main.event_article_fragment.view.*


class EventArticleHolder(inflater: LayoutInflater, parent:ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.event_article_fragment,parent,false)) {

    var eaImage: ImageView? = null
    var eaTitle: TextView? = null
    var eaBody: TextView? = null
    var articleAuthorName: TextView? = null
    var articleScore: TextView? = null
    var articleChangeDate: TextView? = null
    init {
        eaImage = itemView.findViewById(R.id.eaImage)
        eaTitle = itemView.findViewById(R.id.eaTitle)
        eaBody = itemView.findViewById(R.id.eaBody)
        articleAuthorName = itemView.findViewById(R.id.articleAuthorName)
    }

    fun bind(item: EventArticleModel) {
        eaBody?.text = item.body
        eaTitle?.text = item.title
        eaImage?.setImageResource(item.image)
        eaImage?.tag = item.image
        articleAuthorName?.text = "Author: \n" + item.authorName + " " + item.authorSurname
    }

}

data class EventArticleModel(
     val image: Int,
     val title: String,
     val body: String,
     val authorName: String?,
     val authorSurname: String?,
     val score: Double?,
     val changeDate: String?

)
