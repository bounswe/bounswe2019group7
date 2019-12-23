package com.example.app.tradersapp

import android.content.Context
import android.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommentHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.comment_recycler_item,parent,false)) {

    var commentBody: TextView? = null
    var commentAuthorName: TextView? = null
    var commentChangeDate: TextView? = null
    var deleteComment: Button? = null
    var updateComment: Button? = null
    var updateCommentEditText: EditText? = null
    var approveUpdateButton: Button? = null
    var commentId: String? = null
    var userId: String? = null
    var userEmail: String? = null


    init {
        commentBody = itemView.findViewById(R.id.commentBodyTv)
        commentAuthorName = itemView.findViewById(R.id.commentAuthorTv)
        commentChangeDate = itemView.findViewById(R.id.commentDateTv)
        deleteComment = itemView.findViewById(R.id.deleteCommentButton)
        updateComment = itemView.findViewById(R.id.updateCommentButton)
        updateCommentEditText = itemView.findViewById(R.id.updateCommentEditText)
        approveUpdateButton = itemView.findViewById(R.id.approveUpdateButton)
    }

    fun bind(item: CommentModel, context: Context) {
        commentBody?.text = item.body
        commentAuthorName?.text = item.authorName + " " + item.authorSurname
        commentChangeDate?.text = item.changeDate?.substring(0, 10)     // Only show the date, not the hour
        userId = item.userId
        userEmail = item.userEmail
        commentId = item.id
    }

}

data class CommentModel(
    val articleEventId: String?,
    val body: String?,
    val authorName: String?,
    val authorSurname: String?,
    val userId: String?,
    val userEmail: String?,
    val changeDate: String?,
    val id: String?
)
