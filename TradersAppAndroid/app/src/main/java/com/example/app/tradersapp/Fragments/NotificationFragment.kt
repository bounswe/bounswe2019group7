package com.example.app.tradersapp.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.app.tradersapp.NotificationsAdapter
import com.example.app.tradersapp.R
import com.example.app.tradersapp.SelfNotificationsResponse
import kotlinx.android.synthetic.main.followers_fragment.*

class NotificationFragment: Fragment() {

    val notificationsAdapter: NotificationsAdapter = NotificationsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.onViewCreated(view, savedInstanceState)

        notificationsList.apply {
            adapter = notificationsAdapter
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.followers_fragment, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(isFollowers: Boolean): NotificationFragment {
            return NotificationFragment().apply {
                arguments = Bundle().apply {

                }
            }
        }
    }

}


