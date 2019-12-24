package com.example.app.tradersapp

import android.content.Context
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.app.tradersapp.Fragments.ProfileFragment
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
        val sp = PreferenceManager.getDefaultSharedPreferences(mContext)

        holder.commentAuthorName?.setOnClickListener {
            val profileFragment = ProfileFragment()
            val profileBundle = Bundle()
            profileBundle.apply{
                if(holder.userId == sp.getString("userId", null)){
                    putString("email", null)
                }
                else{
                    putString("email", holder.userEmail)
                }
            }
            profileFragment.arguments = profileBundle

            val transaction = (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, profileFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        if(holder.userId == sp.getString("userId","")){
            holder.deleteComment?.visibility = View.VISIBLE
            holder.updateComment?.visibility = View.VISIBLE
        }

            val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

            holder.deleteComment?.setOnClickListener {
                retrofitService.deleteComment(sp.getString("token", ""), holder.commentId).enqueue(object: Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        EyeTradeUtils.toastErrorMessage(mContext, t)
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        list.removeAt(position)
                        notifyItemRemoved(position)
                        Toast.makeText(
                            mContext,
                            "Your comment has been deleted!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            }

            holder.updateComment?.setOnClickListener {
                holder.commentBody?.visibility = View.GONE
                holder.updateComment?.visibility = View.GONE
                holder.approveUpdateButton?.visibility = View.VISIBLE
                holder.updateCommentEditText?.visibility = View.VISIBLE
                holder.updateCommentEditText?.setText(holder.commentBody?.text)


                holder.approveUpdateButton?.setOnClickListener {
                    retrofitService.updateComment(
                        sp.getString("token", ""),
                        holder.commentId,
                        holder.updateCommentEditText?.text.toString()). enqueue(object: Callback<CommentResponse>{
                        override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                            EyeTradeUtils.toastErrorMessage(mContext, t)
                        }

                        override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                            val resp = response.body()
                            val commentModel = CommentModel(
                                resp?.articleEventId,
                                resp?.content,
                                resp?.userInfo?.name,
                                resp?.userInfo?.surname,
                                resp?.userInfo?.id,
                                resp?.userInfo?.email,
                                resp?.createdDate,
                                resp?.id)
                            list.removeAt(position)
                            notifyItemRemoved(position)
                            list.add(position, commentModel)
                            notifyItemInserted(position)

                            holder.approveUpdateButton?.visibility = View.GONE
                            holder.updateComment?.visibility = View.VISIBLE
                            holder.updateCommentEditText?.visibility = View.GONE
                            holder.commentBody?.visibility = View.VISIBLE
                        }

                    })
                }

            }



    }
    override fun getItemCount(): Int = list.size


}

