package com.example.app.tradersapp.Fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.app.tradersapp.*

import kotlinx.android.synthetic.main.fragment_article_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ArticleDetailFragment : Fragment() {

    private val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
    private var sp: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sp = PreferenceManager.getDefaultSharedPreferences(activity)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        aImage.setImageResource(bundle!!.getInt("image"))
        aTitle.text = bundle.getString("title")
        aBody.text = bundle.getString("body")
        articleAuthorName.text = bundle.getString("author")

        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            val token = sp?.getString("token", "")
            val articleId = bundle.getString("articleId")
            retrofitService.givePointToArticle(token, articleId, rating.toDouble()).enqueue(object:
                Callback<ArticleResponse>{
                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    EyeTradeUtils.toastErrorMessage(activity as Context, t)
                }

                override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                    Toast.makeText(
                        context,
                        "Your rating has been saved successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        }
    }


}