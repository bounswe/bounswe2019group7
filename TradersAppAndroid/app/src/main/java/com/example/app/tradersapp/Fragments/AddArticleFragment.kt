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
import kotlinx.android.synthetic.main.fragment_add_article.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddArticleFragment : Fragment() {

    private val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
    private var sp: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createArticleButton.setOnClickListener {
            sp = PreferenceManager.getDefaultSharedPreferences(activity)
            val titleEditText = title
            val bodyEditText = body
            val token = (sp as SharedPreferences).getString("token", "")
            val title = titleEditText.text.toString()
            val body = bodyEditText.text.toString()
            retrofitService.getSelfProfileInformation(token).enqueue(object: Callback<SelfProfileInformationResponse>{
                override fun onFailure(call: Call<SelfProfileInformationResponse>, t: Throwable) {
                    EyeTradeUtils.toastErrorMessage(activity as Context, t)
                }

                override fun onResponse(
                    call: Call<SelfProfileInformationResponse>,
                    response: Response<SelfProfileInformationResponse>
                ) {
                    val resp = response.body()
                    val requestBody = ArticleInformation(resp?.email, resp?.name, resp?.surname, body, title)

                    retrofitService.createArticle(token, requestBody).enqueue(object: Callback<ArticleResponse>{
                        override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                            EyeTradeUtils.toastErrorMessage(activity as Context, t)
                        }

                        override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                            Toast.makeText(
                                activity,
                                "Your article is successfully published!",
                                Toast.LENGTH_SHORT
                            ).show()
                            activity?.onBackPressed()    // close the fragment
                        }
                    })
                }
            })
        }
    }
}
