package com.example.app.tradersapp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.app.tradersapp.Fragments.*
import com.example.app.tradersapp.RegistrationActivity.RegisterCallback.activity


class EventAdapter(private val list: List<EventModel>, context: Context)
        : RecyclerView.Adapter<EventHolder>() {

        val mContext = context
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
            val inflater = LayoutInflater.from(parent.context)
            return EventHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: EventHolder, position: Int) {
            val eaModel: EventModel = list[position]
            holder.bind(eaModel)

            holder.itemView.setOnClickListener {
                val eventDetailFragment = EventDetailFragment()
                val bundle = Bundle()
                bundle.apply{
                    putInt("image", holder.eaImage!!.tag as Int)
                    putString("title", holder.eaTitle!!.text.toString())
                    putString("body", holder.eaBody!!.text.toString())
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

