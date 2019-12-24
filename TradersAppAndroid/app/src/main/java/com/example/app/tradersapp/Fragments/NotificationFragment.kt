package com.example.app.tradersapp.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.app.tradersapp.*
import kotlinx.android.synthetic.main.followers_fragment.*
import kotlinx.android.synthetic.main.fragment_article_detail.*
import kotlinx.android.synthetic.main.fragment_article_detail.rvComments
import kotlinx.android.synthetic.main.fragment_article_detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragment: Fragment() {

    private val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
    private var sp: SharedPreferences? = null
    private var notificationslist: MutableList<SelfNotificationsResponse> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = PreferenceManager.getDefaultSharedPreferences(activity)
        val token = sp?.getString("token", null)

        retrofitService.getSelfNotifications(token).enqueue(object:
            Callback<MutableList<SelfNotificationsResponse>> {
            override fun onFailure(call: Call<MutableList<SelfNotificationsResponse>>, t: Throwable) {
                EyeTradeUtils.toastErrorMessage(activity as Context, t)
            }

            override fun onResponse(call: Call<MutableList<SelfNotificationsResponse>>, response: Response<MutableList<SelfNotificationsResponse>>) {
                notificationslist = response.body() ?: mutableListOf()

                notificationsList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = NotificationsAdapter(notificationslist, context)
                    addItemDecoration(DividerItemDecoration(notificationsList.context, LinearLayoutManager.VERTICAL))
                }

            }

        })

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


