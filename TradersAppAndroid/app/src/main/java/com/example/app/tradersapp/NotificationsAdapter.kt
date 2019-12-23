package com.example.app.tradersapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

class NotificationsAdapter: RecyclerView.Adapter<NotificationsAdapter.NotificationssViewHolder>() {
    lateinit var items: List<SelfNotificationsResponse>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationssViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NotificationssViewHolder(inflater, parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: NotificationssViewHolder, position: Int) {
        holder.bind(items[position])
    }


    inner class NotificationssViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_new_follower, parent, false)) {

        var newFollowerName: TextView = itemView.findViewById(R.id.newFollowerName)


        fun bind(notificationsResponse: SelfNotificationsResponse) {

            newFollowerName.text = notificationsResponse.followerName
        }
    }
}