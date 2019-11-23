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


class ArticleAdapter(private val list: List<ArticleModel>, context: Context)
        : RecyclerView.Adapter<ArticleHolder>() {

        val mContext = context
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ArticleHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
            val eaModel: ArticleModel = list[position]
            holder.bind(eaModel)

            holder.itemView.setOnClickListener {
                val articleDetailFragment = ArticleDetailFragment()
                val bundle = Bundle()
                bundle.apply{
                    putInt("image", holder.eaImage!!.tag as Int)
                    putString("title", holder.eaTitle!!.text.toString())
                    putString("body", holder.eaBody!!.text.toString())
                    putString("author", holder.articleAuthorName!!.text.toString())
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

