package com.example.app.tradersapp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.app.tradersapp.Fragments.*


class UserAdapter(private val list: List<UserModel>, context: Context)
        : RecyclerView.Adapter<UserHolder>() {

        val mContext = context
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
            val inflater = LayoutInflater.from(parent.context)
            return UserHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: UserHolder, position: Int) {
            val uModel: UserModel = list[position]
            holder.bind(uModel)

            holder.itemView.setOnClickListener {
                val eventDetailFragment = ProfileFragment()
                val bundle = Bundle()
                bundle.apply{
                    putString("email", holder.uEmail)
                }
                eventDetailFragment.arguments = bundle

                val transaction = (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, eventDetailFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        override fun getItemCount(): Int = list.size
    }

