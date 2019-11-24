package com.example.app.tradersapp.Fragments

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.app.tradersapp.R


class UserHolder(inflater: LayoutInflater, parent:ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.user_recycler_item,parent,false)) {

    var uName: TextView? = null
    var uEmail: String? = null
    init {
        uName = itemView.findViewById(R.id.uName)
    }

    fun bind(item: UserModel) {
        uName?.text = "${item.name} ${item.surname}"
        uEmail = item.email
    }

}

data class UserModel(
     val name: String,
     val surname: String,
     val email: String
)
