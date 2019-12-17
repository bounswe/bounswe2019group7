package com.example.app.tradersapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.app.tradersapp.RegistrationActivity.RegisterCallback.activity
import kotlinx.android.synthetic.main.fragment_article_detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommentAdapter(private val list: List<CommentModel>, context: Context)
    : RecyclerView.Adapter<CommentHolder>() {

    val mContext = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CommentHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val commentModel: CommentModel = list[position]
        holder.bind(commentModel)
    }
    override fun getItemCount(): Int = list.size
}

