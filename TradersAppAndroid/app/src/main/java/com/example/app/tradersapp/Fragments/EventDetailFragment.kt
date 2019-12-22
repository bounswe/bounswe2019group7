package com.example.app.tradersapp.Fragments


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.app.tradersapp.*

import kotlinx.android.synthetic.main.fragment_event_detail.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EventDetailFragment : Fragment() {

    private val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
    private var sp: SharedPreferences? = null
    private var allComments: List<CommentModel> = emptyList()
    private var allAnnotations: List<AnnotationResponse> = emptyList()
    private var isInAnnotationMode = false
    private var isInSelfAnnotationMode = false
    private var eventId: String = ""

    private var body: String = ""

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
        body = bundle.getString("body")
        eBody.text = body
        val token = sp?.getString("token", "")
        eventId = bundle.getString("eventId")

        eBody.customSelectionActionModeCallback = AnnotationsActionModeCallback(
            eBody, context, token, retrofitService, eventId, annotationBody = addCommentEditText2, allAnnotations = allAnnotations)

        showAnnotationsButton2.setOnClickListener {
            if(isInAnnotationMode){
                switchToReadingMode()
            }
            else{
                switchToAnnotationMode(eventId)
            }
        }

        myAnnotationsButton2.setOnClickListener {
            isInSelfAnnotationMode = true
            getSelfAnnotationsInArticleOrEvent(eventId)
        }

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
                        "Event",
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

    private fun getComments(token: String?, eventId: String){
        retrofitService.getAllCommentsOfArticleOrEvent(token, eventId).enqueue(object:
            Callback<List<CommentResponse>> {
            override fun onFailure(call: Call<List<CommentResponse>>, t: Throwable) {
                EyeTradeUtils.toastErrorMessage(activity as Context, t)
            }

            override fun onResponse(call: Call<List<CommentResponse>>, response: Response<List<CommentResponse>>) {
                allComments = response.body()?.map {
                    CommentModel(it.articleEventId, it.content, it.userInfo.name, it.userInfo.surname, it.createdDate, it.id)
                }?: emptyList()

                rvComments2.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CommentAdapter(allComments, context)
                    addItemDecoration(DividerItemDecoration(rvComments2.context, LinearLayoutManager.VERTICAL))
                }

            }

        })
    }

    private fun switchToReadingMode(){
        isInSelfAnnotationMode = false
        eBody.setTextIsSelectable(false)
        showAnnotationsButton2.text = "SWITCH TO ANNOTATION MODE"
        isInAnnotationMode = false
        revertHighlightText()

        addCommentButton2.visibility = View.VISIBLE
        addCommentEditText2.hint = "Write your comment here"
    }
    private fun switchToAnnotationMode(eventId: String){
        isInSelfAnnotationMode = false
        isInAnnotationMode = true
        showAnnotationsButton2.text = "SWITCH TO READING MODE"
        getAllAnnotations(eventId)   // Get all annotations and highlight the text accordingly.
        eBody.setTextIsSelectable(true)

        addCommentButton2.visibility = View.GONE
        addCommentEditText2.hint = "Write your annotation here"

    }

    private fun highlightText(startIndex: Int, endIndex: Int){
        val textToSpan: Spannable = SpannableString(eBody.text)
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                if(!isInSelfAnnotationMode){
                    var annotationContent = ""
                    for(annotation in allAnnotations){
                        if(startIndex == annotation.firstChar){
                            annotationContent += "\n" + annotation.user.name + " " + annotation.user.surname + ": " + annotation.content + "\n"
                        }
                    }

                    Toast.makeText(
                        context,
                        annotationContent,
                        Toast.LENGTH_LONG
                    ).show()
                }
                else{
                    for(annotation in allAnnotations){
                        if(startIndex == annotation.firstChar){
                            deleteAnnotation(annotation.id)
                        }
                    }

                }
            }
        }
        textToSpan.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        if(!isInSelfAnnotationMode){
            textToSpan.setSpan(ForegroundColorSpan(Color.YELLOW), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        eBody.text = textToSpan
        eBody.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun revertHighlightText(){
        eBody.text = body
    }

    private fun getAllAnnotations(eventId: String){
        retrofitService.getAllAnnotationsOfArticleOrEvent("Article", eventId).enqueue(object: Callback<List<AnnotationResponse>>{
            override fun onFailure(call: Call<List<AnnotationResponse>>, t: Throwable) {
                EyeTradeUtils.toastErrorMessage(context!!, t)
            }

            override fun onResponse(call: Call<List<AnnotationResponse>>, response: Response<List<AnnotationResponse>>) {
                revertHighlightText()
                allAnnotations = response.body() ?: emptyList()
                for(annotation in allAnnotations){
                    highlightText(annotation.firstChar, annotation.lastChar)
                }
            }

        })
    }

    private fun getSelfAnnotationsInArticleOrEvent(eventId: String){
        retrofitService.getSelfAnnotations(sp?.getString("token", "")).enqueue(object: Callback<List<AnnotationResponse>>{
            override fun onFailure(call: Call<List<AnnotationResponse>>, t: Throwable) {
                EyeTradeUtils.toastErrorMessage(context!!, t)
            }

            override fun onResponse(call: Call<List<AnnotationResponse>>, response: Response<List<AnnotationResponse>>) {
                val selfAnnotations = response.body() ?: emptyList()
                for(annotation in selfAnnotations){
                    if(annotation.articleEventId == eventId){
                        highlightText(annotation.firstChar, annotation.lastChar)
                    }
                }
            }

        })
    }

    private fun deleteAnnotation(annotationId: String){
        retrofitService.deleteAnnotation(sp?.getString("token", ""), annotationId).enqueue(object: Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                EyeTradeUtils.toastErrorMessage(activity as Context, t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(
                    context,
                    "Annotation is successfully deleted",
                    Toast.LENGTH_LONG
                ).show()
                getAllAnnotations(eventId)
            }

        })
    }


}
