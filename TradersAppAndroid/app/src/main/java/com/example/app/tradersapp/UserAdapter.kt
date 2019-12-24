package com.example.app.tradersapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.app.tradersapp.Fragments.*


class UserAdapter(private val list: List<UserModel>, context: Context)
        : RecyclerView.Adapter<UserHolder>() {

        val mContext = context
        private var sp: SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(mContext)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
            val inflater = LayoutInflater.from(parent.context)
            return UserHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: UserHolder, position: Int) {
            val uModel: UserModel = list[position]
            holder.bind(uModel)

            holder.itemView.setOnClickListener {
                val profileFragment = ProfileFragment()
                val bundle = Bundle()
                bundle.apply{
                    if(holder.uEmail == sp?.getString("userEmail", null)){
                        putString("email", null)
                    }
                    else{
                        putString("email", holder.uEmail)
                    }

                }
                profileFragment.arguments = bundle

                val transaction = (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, profileFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        override fun getItemCount(): Int = list.size
    }

