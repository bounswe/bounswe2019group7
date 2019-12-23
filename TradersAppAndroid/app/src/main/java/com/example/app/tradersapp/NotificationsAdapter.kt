package com.example.app.tradersapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.app.tradersapp.Fragments.ProfileFragment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class NotificationsAdapter(private val items: MutableList<SelfNotificationsResponse>,  context: Context): RecyclerView.Adapter<NotificationsAdapter.NotificationssViewHolder>() {
    private val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
    private var sp: SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(context)
    val token = sp?.getString("token", null)
    val mContext = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationssViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NotificationssViewHolder(inflater, parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: NotificationssViewHolder, position: Int) {
        holder.bind(items[position])
        holder.deleteNotificationButton.setOnClickListener {
            retrofitService.deleteNotification(token, holder.notificationId).enqueue(object: retrofit2.Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    EyeTradeUtils.toastErrorMessage(mContext, t)
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    items.removeAll { it.id == holder.notificationId }
                    notifyDataSetChanged()
                }

            })
        }
        holder.itemView.setOnClickListener {
            val profileFragment = ProfileFragment()
            val profileBundle = Bundle()
            profileBundle.apply{
                putString("email", holder.email)
            }
            profileFragment.arguments = profileBundle

            val transaction = (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, profileFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }


    inner class NotificationssViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_new_follower, parent, false)) {

        var newFollowerName: TextView = itemView.findViewById(R.id.newFollowerName)
        var deleteNotificationButton: Button = itemView.findViewById(R.id.deleteNotificationButton)
        var notificationId: String = ""
        var email: String = ""


        fun bind(notificationsResponse: SelfNotificationsResponse) {
            newFollowerName.text = notificationsResponse.followerName + " " + notificationsResponse.followerSurname + " followed you!"
            notificationId = notificationsResponse.id
            email = notificationsResponse.followerEmail
        }
    }

}