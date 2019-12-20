package com.example.app.tradersapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_article_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnnotationsActionModeCallback(val bodyTextView: TextView,
                                    val mContext: Context?,
                                    val token: String?,
                                    val retrofitService: ApiInterface,
                                    val articleEventId: String
) : android.view.ActionMode.Callback {
    override fun onActionItemClicked(mode: android.view.ActionMode?, item: MenuItem?): Boolean {
        val startIndex = bodyTextView.selectionStart
        val endIndex = bodyTextView.selectionEnd

        Toast.makeText(
            mContext,
             startIndex.toString() + " ----- " + endIndex.toString(),
            Toast.LENGTH_SHORT
        ).show()

        retrofitService.addAnnotation(token,
            AnnotationInformation(articleEventId,
                "Article" ,
                "Good point!",
                startIndex,
                endIndex)).enqueue(object: Callback<AnnotationResponse>{
            override fun onFailure(call: Call<AnnotationResponse>, t: Throwable) {
                EyeTradeUtils.toastErrorMessage(mContext!!,t)
            }

            override fun onResponse(call: Call<AnnotationResponse>, response: Response<AnnotationResponse>) {
                 highlightText(startIndex, endIndex)
            }

        })

        return true
    }

    override fun onCreateActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
        menu?.add(0, 0, 0, "Annotate")?.setIcon(R.drawable.abc_ab_share_pack_mtrl_alpha);
        return true;
    }

    override fun onPrepareActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
        //menu?.clear()
        return true;
    }

    override fun onDestroyActionMode(mode: android.view.ActionMode?) {

    }


    fun highlightText(startIndex: Int, endIndex: Int){
        val textToSpan: Spannable = SpannableString(bodyTextView.text)
        textToSpan.setSpan(ForegroundColorSpan(Color.YELLOW), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        bodyTextView.text = textToSpan
    }

}