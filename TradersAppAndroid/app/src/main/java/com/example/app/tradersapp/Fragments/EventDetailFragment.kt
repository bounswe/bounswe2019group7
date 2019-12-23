package com.example.app.tradersapp.Fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.app.tradersapp.*

import kotlinx.android.synthetic.main.fragment_event_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EventDetailFragment : Fragment() {

    private val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
    private var sp: SharedPreferences? = null
    private var allComments: List<CommentModel> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sp = PreferenceManager.getDefaultSharedPreferences(activity)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        eImage.setImageResource(bundle!!.getInt("image"))
        eTitle.text = bundle.getString("title")
        eBody.text = bundle.getString("body")
        val token = sp?.getString("token", "")
        val eventId = bundle.getString("eventId")

        addCommentButton2.setOnClickListener {
            if(addCommentEditText2.text.isNullOrEmpty()){
                Toast.makeText(
                    activity,
                    "Write a comment before sending it!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                retrofitService.addComment(
                    token,
                    CommentInformation(
                        eventId,
                        "Article",
                        addCommentEditText2.text.toString(),
                        "Comment Title"
                    )
                ).enqueue(object: Callback<CommentResponse>{
                    override fun onFailure(call: Call<CommentResponse>, t: Throwable) {
                        EyeTradeUtils.toastErrorMessage(RegistrationActivity.RegisterCallback.activity as Context, t)
                    }

                    override fun onResponse(call: Call<CommentResponse>, response: Response<CommentResponse>) {
                        Toast.makeText(
                            activity,
                            "Your comment has been saved successfully.",
                            Toast.LENGTH_SHORT
                        ).show()
                        addCommentEditText2.hideKeyboard()
                        addCommentEditText2.text = null
                        getComments(token, eventId)
                    }
                })
            }
        }
        getComments(token, eventId)

    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun getComments(token: String?, articleId: String){
        retrofitService.getAllCommentsOfArticleOrEvent(token, articleId).enqueue(object:
            Callback<List<CommentResponse>> {
            override fun onFailure(call: Call<List<CommentResponse>>, t: Throwable) {
                EyeTradeUtils.toastErrorMessage(activity as Context, t)
            }

            override fun onResponse(call: Call<List<CommentResponse>>, response: Response<List<CommentResponse>>) {
                allComments = response.body()?.map {
                    CommentModel(it.articleEventId, it.content, it.userInfo.name, it.userInfo.surname, it.userInfo.id, it.createdDate, it.id)
                }?: emptyList()

                rvComments2.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CommentAdapter(allComments, context)
                    addItemDecoration(DividerItemDecoration(rvComments2.context, LinearLayoutManager.VERTICAL))
                }

            }

        })
    }


}
