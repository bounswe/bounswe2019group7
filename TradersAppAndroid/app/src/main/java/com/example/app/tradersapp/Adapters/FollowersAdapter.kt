package com.example.app.tradersapp.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.app.tradersapp.FollowersResponse
import com.example.app.tradersapp.R

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    lateinit var items: List<FollowersResponse>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FollowersViewHolder(inflater, parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class FollowersViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_follower, parent, false)) {

        var textViewName: TextView = itemView.findViewById(R.id.userName)
        var imageView: ImageView = itemView.findViewById(R.id.userImage)

        fun bind(followersResponse: FollowersResponse) {
            //TODO: LOAD IMAGE TO IMAGEVİEW
            textViewName.text = followersResponse.name + " " + followersResponse.surname
        }
    }

}
