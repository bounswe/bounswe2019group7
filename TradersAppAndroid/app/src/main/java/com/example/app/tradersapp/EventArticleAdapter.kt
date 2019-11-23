package com.example.app.tradersapp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.app.tradersapp.Fragments.ArticleDetailFragment
import com.example.app.tradersapp.Fragments.EventArticleHolder
import com.example.app.tradersapp.Fragments.EventArticleModel
import com.example.app.tradersapp.RegistrationActivity.RegisterCallback.activity


class EventArticleAdapter(private val list: List<EventArticleModel>, context: Context)
        : RecyclerView.Adapter<EventArticleHolder>() {

        val mContext = context
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventArticleHolder {
            val inflater = LayoutInflater.from(parent.context)
            return EventArticleHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: EventArticleHolder, position: Int) {
            val eaModel: EventArticleModel = list[position]
            holder.bind(eaModel)
            var fragment: Fragment? = null
            holder.itemView.setOnClickListener {
                // TODO: Open the article
                val articleDetailFragment = ArticleDetailFragment()
                val bundle = Bundle()
                bundle.apply{
                    putInt("image", holder.eaImage!!.tag as Int)
                    putString("title", holder.eaTitle!!.text.toString())
                    putString("body", holder.eaBody!!.text.toString())
                }
                articleDetailFragment.arguments = bundle
                val transaction = (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, articleDetailFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

        override fun getItemCount(): Int = list.size

    }

