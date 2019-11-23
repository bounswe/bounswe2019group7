package com.example.app.tradersapp.Fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.app.tradersapp.R


class ArticleHolder(inflater: LayoutInflater, parent:ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.article_recycler_item,parent,false)) {

    var aImage: ImageView? = null
    var aTitle: TextView? = null
    var aBody: TextView? = null
    var articleAuthorName: TextView? = null
    var articleScore: RatingBar
    var articleChangeDate: TextView? = null
    init {
        aImage = itemView.findViewById(R.id.aImage)
        aTitle = itemView.findViewById(R.id.aTitle)
        aBody = itemView.findViewById(R.id.aBody)
        articleAuthorName = itemView.findViewById(R.id.articleAuthorName)
        articleScore = itemView.findViewById(R.id.articleRatingBar)
    }

    fun bind(item: ArticleModel) {
        aBody?.text = item.body
        aTitle?.text = item.title
        aImage?.setImageResource(item.image)
        aImage?.tag = item.image
        articleAuthorName?.text = "Author: \n" + item.authorName + " " + item.authorSurname
        articleScore?.rating = item.score.toFloat()
    }

}

data class ArticleModel(
     val image: Int,
     val title: String,
     val body: String,
     val authorName: String,
     val authorSurname: String,
     val score: Double,
     val changeDate: String

)
