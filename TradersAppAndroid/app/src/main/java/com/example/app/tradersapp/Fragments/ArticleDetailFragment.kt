package com.example.app.tradersapp.Fragments


import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
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
import kotlinx.android.synthetic.main.fragment_article_detail.*
import kotlinx.android.synthetic.main.fragment_article_detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ArticleDetailFragment : Fragment() {

    private val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
    private var sp: SharedPreferences? = null
    private var allComments: List<CommentModel> = emptyList()
    private var allAnnotations: List<AnnotationResponse> = emptyList()
    private var isInAnnotationMode = false

    private var body: String = ""

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
        body  =  bundle.getString("body")
        aBody.text = body
        articleAuthorName.text = bundle.getString("author")
        val token = sp?.getString("token", "")
        val articleId = bundle.getString("articleId")

        showAnnotationContentsWhenLongPressed()

        aBody.customSelectionActionModeCallback = AnnotationsActionModeCallback(
            aBody, context, token, retrofitService, articleId, annotationBody = addCommentEditText, allAnnotations = allAnnotations)

        showAnnotationsButton.setOnClickListener {
            if(isInAnnotationMode){
                switchToReadingMode()
            }
            else{
                switchToAnnotationMode(articleId)
            }
        }

        addCommentButton.setOnClickListener {
            if(addCommentEditText.text.isNullOrEmpty()){
                Toast.makeText(
                    activity,
                    "Write a comment to send it!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                retrofitService.addComment(
                    token,
                    CommentInformation(
                        articleId,
                        "Article",
                        addCommentEditText.text.toString(),
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
                        addCommentEditText.hideKeyboard()
                        addCommentEditText.text = null
                        getComments(token, articleId)
                    }
                })
            }
        }

        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->

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
        articleAuthorName.setOnClickListener {
            val profileFragment = ProfileFragment()
            val profileBundle = Bundle()
            profileBundle.apply{
                putString("email", bundle.getString("email"))
            }
            profileFragment.arguments = profileBundle

            val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, profileFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        getComments(token, articleId)
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    private fun getComments(token: String?, articleId: String){
        retrofitService.getAllCommentsOfArticleOrEvent(token, articleId).enqueue(object: Callback<List<CommentResponse>>{
            override fun onFailure(call: Call<List<CommentResponse>>, t: Throwable) {
                EyeTradeUtils.toastErrorMessage(activity as Context, t)
            }

            override fun onResponse(call: Call<List<CommentResponse>>, response: Response<List<CommentResponse>>) {
                allComments = response.body()?.map {
                    CommentModel(it.articleEventId, it.content, it.userInfo.name, it.userInfo.surname, it.createdDate, it.id)
                }?: emptyList()

                rvComments.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CommentAdapter(allComments, context)
                    addItemDecoration(DividerItemDecoration(rvComments.context, LinearLayoutManager.VERTICAL))
                }

            }

        })

    }
    private fun switchToReadingMode(){
        aBody.setTextIsSelectable(false)
        showAnnotationsButton.text = "SWITCH TO ANNOTATION MODE"
        isInAnnotationMode = false
        revertHighlightText()

        addCommentButton.visibility = View.VISIBLE
        addCommentEditText.hint = "Write your comment here"
    }
    private fun switchToAnnotationMode(articleId: String){
        isInAnnotationMode = true
        showAnnotationsButton.text = "SWITCH TO READING MODE"
        getAllAnnotations(articleId)   // Get all annotations and highlight the text accordingly.
        aBody.setTextIsSelectable(true)

        addCommentButton.visibility = View.GONE
        addCommentEditText.hint = "Write your annotation here"

    }

    private fun highlightText(startIndex: Int, endIndex: Int){
        val textToSpan: Spannable = SpannableString(aBody.text)
        textToSpan.setSpan(ForegroundColorSpan(Color.YELLOW), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
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
        }
        textToSpan.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textToSpan.setSpan(ForegroundColorSpan(Color.YELLOW), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        aBody.text = textToSpan
        aBody.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun revertHighlightText(){
        aBody.text = body
    }

    private fun getAllAnnotations(articleId: String){
        retrofitService.getAllAnnotationsOfArticleOrEvent("Article", articleId).enqueue(object: Callback<List<AnnotationResponse>>{
            override fun onFailure(call: Call<List<AnnotationResponse>>, t: Throwable) {
                EyeTradeUtils.toastErrorMessage(context!!, t)
            }

            override fun onResponse(call: Call<List<AnnotationResponse>>, response: Response<List<AnnotationResponse>>) {
                allAnnotations = response.body() ?: emptyList()
                for(annotation in allAnnotations){
                    highlightText(annotation.firstChar, annotation.lastChar)
                }
            }

        })
    }

    private fun showAnnotationContentsWhenLongPressed(){
        val ss: SpannableString = SpannableString(aBody.text.toString())
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                Toast.makeText(
                    context,
                    "ANNOTATED STRING IS CLICKED",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        for(annotation in allAnnotations){
            ss.setSpan(clickableSpan, annotation.firstChar, annotation.lastChar, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        aBody.text = ss
    }

}
