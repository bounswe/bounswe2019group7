package com.example.app.tradersapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.app.tradersapp.RegistrationActivity.RegisterCallback.activity
import kotlinx.android.synthetic.main.fragment_article_detail.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CommentAdapter(private var list: MutableList<CommentModel>, context: Context)
    : RecyclerView.Adapter<CommentHolder>() {

    val mContext = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CommentHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val commentModel: CommentModel = list[position]
        holder.bind(commentModel, mContext)
        holder.deleteComment?.setOnClickListener {
            val sp = PreferenceManager.getDefaultSharedPreferences(mContext)
            if(holder.userId == sp.getString("userId","")){

                val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
                retrofitService.deleteComment(sp.getString("token", ""), holder.commentId).enqueue(object: Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        EyeTradeUtils.toastErrorMessage(mContext, t)
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        list.removeAll{it.id == holder.commentId}
                        notifyDataSetChanged()
                        Toast.makeText(
                            mContext,
                            "Your comment has been deleted!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            }
            else{
                Toast.makeText(
                    mContext,
                    "You cannot delete other people's comments!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    override fun getItemCount(): Int = list.size


}

