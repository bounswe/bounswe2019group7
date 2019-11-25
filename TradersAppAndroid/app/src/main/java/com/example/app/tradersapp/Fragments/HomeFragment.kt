package com.example.app.tradersapp.Fragments


import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.app.tradersapp.*
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var sp:SharedPreferences? = null

    private var suggestedArticles: List<ArticleModel> = emptyList()
    private var suggestedEvents: List<EventModel> = emptyList()
    private var suggestedUsers: List<UserModel> = emptyList()

    val images = arrayOf(R.drawable.article1, R.drawable.article2, R.drawable.article3, R.drawable.article4, R.drawable.article5, R.drawable.article6)

    val imagePlaceholder = R.drawable.placeholder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        EyeTradeUtils.showSpinner(activity)

        sp = PreferenceManager.getDefaultSharedPreferences(context)

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        retrofitService.getSelfProfileInformation(sp?.getString("token",null)).enqueue(object: Callback<SelfProfileInformationResponse>{
            override fun onFailure(call: Call<SelfProfileInformationResponse>, t: Throwable) {
                Log.i("ApiRequest", "Request failed: " + t.toString())
                homeGreeting.text = "Hi, Guest!"
            }

            override fun onResponse(call: Call<SelfProfileInformationResponse>, response: Response<SelfProfileInformationResponse>) {
                if (response.code() == 200) {
                    val body = response.body()
                    homeGreeting.text = "Hi, ${body?.name}!"
                }else{
                    homeGreeting.text = "Hi, Guest!"
                }
            }
        })

        retrofitService.getHomeFeed().enqueue(object: Callback<HomeFeedResponse>{
            override fun onFailure(call: Call<HomeFeedResponse>, t: Throwable) {
                Log.i("ApiRequest", "Request failed: " + t.toString())
                Toast.makeText(activity, "Could not get home feed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<HomeFeedResponse>, response: Response<HomeFeedResponse>) {
                if (response.code() == 200) {
                    val body = response.body()
                    // load home feed
                    suggestedArticles = body?.suggestedArticles?.map {
                        ArticleModel(
                            images[abs(it.uuid.hashCode())%images.size],
                            it.title,
                            it.content,
                            it.authorName,
                            it.authorSurname,
                            it.authorEmail,
                            it.score,
                            it.changeDate,
                            it.uuid)
                    } ?: suggestedArticles

                    suggestedEvents = body?.suggestedEvents?.map {
                        EventModel(
                            images[abs(it.guid.hashCode())%images.size],
                            it.title,
                            it.content
                        )
                    } ?: suggestedEvents

                    suggestedUsers = body?.suggestedUsers?.map {
                        UserModel(
                            it.name,
                            it.surname,
                            it.email
                        )
                    } ?: suggestedUsers

                    homeArticleList.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = ArticleAdapter(suggestedArticles, context)
                        addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                    }
                    homeEventList.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = EventAdapter(suggestedEvents, context)
                        addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                    }
                    homeUserList.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = UserAdapter(suggestedUsers, context)
                        addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                    }
                    EyeTradeUtils.hideSpinner(activity)
                }else{
                    Toast.makeText(activity, "Could not get home feed: "+response.code(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


}
