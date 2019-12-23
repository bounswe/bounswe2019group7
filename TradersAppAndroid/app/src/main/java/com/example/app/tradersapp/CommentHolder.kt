package com.example.app.tradersapp

import android.content.Context
import android.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class CommentHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.comment_recycler_item,parent,false)) {

    var commentBody: TextView? = null
    var commentAuthorName: TextView? = null
    var commentChangeDate: TextView? = null
    var deleteComment: Button? = null
    var commentId: String? = null


    init {
        commentBody = itemView.findViewById(R.id.commentBodyTv)
        commentAuthorName = itemView.findViewById(R.id.commentAuthorTv)
        commentChangeDate = itemView.findViewById(R.id.commentDateTv)
        deleteComment = itemView.findViewById(R.id.deleteCommentButton)
    }

    fun bind(item: CommentModel, context: Context) {
        commentBody?.text = item.body
        commentAuthorName?.text = item.authorName + " " + item.authorSurname
        commentChangeDate?.text = item.changeDate.substring(0, 10)     // Only show the date, not the hour
        deleteComment?.setOnClickListener {
            val sp = PreferenceManager.getDefaultSharedPreferences(context)
            if(item.userId == sp.getString("userId","")){
                Toast.makeText(
                    context,
                    "Your comment has been deleted!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                Toast.makeText(
                    context,
                    "You cannot delete other people's comments!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}

data class CommentModel(
    val articleEventId: String,
    val body: String?,
    val authorName: String,
    val authorSurname: String,
    val userId: String?,
    val changeDate: String,
    val id: String
)
