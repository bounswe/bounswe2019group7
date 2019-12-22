package com.example.app.tradersapp

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnnotationsActionModeCallback(val bodyTextView: TextView,
                                    val mContext: Context?,
                                    val token: String?,
                                    val retrofitService: ApiInterface,
                                    val articleEventId: String,
                                    val annotationBody: EditText,
                                    var allAnnotations: List<AnnotationResponse>,
                                    val annotationType: String

) : android.view.ActionMode.Callback {
    override fun onActionItemClicked(mode: android.view.ActionMode?, item: MenuItem?): Boolean {
        if(annotationBody.text.isNullOrEmpty()){
            Toast.makeText(
                mContext,
                "Please write something to annotate.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        val startIndex = bodyTextView.selectionStart
        val endIndex = bodyTextView.selectionEnd

        retrofitService.addAnnotation(token,
            AnnotationInformation(articleEventId,
                annotationType ,
                annotationBody.text.toString(),
                startIndex,
                endIndex)).enqueue(object: Callback<AnnotationResponse>{
            override fun onFailure(call: Call<AnnotationResponse>, t: Throwable) {
                EyeTradeUtils.toastErrorMessage(mContext!!,t)
            }

            override fun onResponse(call: Call<AnnotationResponse>, response: Response<AnnotationResponse>) {
                allAnnotations += listOf(response.body()!!)
                 highlightText(startIndex, endIndex)
                 Toast.makeText(
                     mContext,
                     "Annotation is successfully added.",
                     Toast.LENGTH_SHORT
                 ).show()
                annotationBody.text = null
            }

        })

        return true
    }

    override fun onCreateActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
        menu?.add(0, 0, 0, "Annotate")?.setIcon(R.drawable.abc_ab_share_pack_mtrl_alpha);
        return true;
    }

    override fun onPrepareActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
        menu?.removeItem(android.R.id.selectAll);
        menu?.removeItem(android.R.id.cut);
        menu?.removeItem(android.R.id.copy);
        menu?.removeItem(android.R.id.shareText)
        return true;
    }

    override fun onDestroyActionMode(mode: android.view.ActionMode?) {

    }


    fun highlightText(startIndex: Int, endIndex: Int){
        val textToSpan: Spannable = SpannableString(bodyTextView.text)
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                var annotationContent = ""
                for(annotation in allAnnotations){
                    if(startIndex == annotation.firstChar && endIndex == annotation.lastChar){
                        annotationContent += "\n" + annotation.user.name + " " + annotation.user.surname + ": " + annotation.content + "\n"
                    }
                }
                Toast.makeText(
                    mContext,
                    annotationContent,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        textToSpan.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textToSpan.setSpan(ForegroundColorSpan(Color.YELLOW), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        bodyTextView.text = textToSpan
    }

}