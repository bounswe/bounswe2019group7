package com.example.app.tradersapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView


class CommentHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.comment_recycler_item,parent,false)) {

    var commentBody: TextView? = null
    var commentAuthorName: TextView? = null
    var commentChangeDate: TextView? = null
    var commentId: String? = null


    init {
        commentBody = itemView.findViewById(R.id.commentBodyTv)
        commentAuthorName = itemView.findViewById(R.id.commentAuthorTv)
        commentChangeDate = itemView.findViewById(R.id.commentDateTv)
    }

    fun bind(item: CommentModel) {
        commentBody?.text = item.body
        commentAuthorName?.text = "Comment Owner: \n" + item.authorName + " " + item.authorSurname
        commentChangeDate?.text = item.changeDate
    }

}

data class CommentModel(
    val articleEventId: String,
    val body: String?,
    val authorName: String,
    val authorSurname: String,
    val changeDate: String,
    val id: String
)
