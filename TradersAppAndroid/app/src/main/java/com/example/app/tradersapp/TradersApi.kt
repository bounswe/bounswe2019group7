package com.example.app.tradersapp

import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


class RetrofitInstance {
    companion object {
        val BASE_URL: String = "http://100.26.202.213:8080/"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}

interface ApiInterface {

    @Headers("Content-Type:application/json")
    @POST("registration/basic_register")
    fun registerBasicUser(
        @Body info: BasicUserInformation,
        @Header("password")password: String
    ): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("registration/trader_register")
    fun registerTraderUser(
        @Body info: TraderUserInformation,
        @Header("password")password: String
    ): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("login")
    fun loginUser(@Body info: LoginInformation): retrofit2.Call<LoginResponse>


    @GET("currency/convert")
    fun getExchangeRate(@Query("inputCurrencyType") currency1: String,
                        @Query("outputCurrencyType") currency2: String,
                        @Query("amount") amount: Double = 1.0): retrofit2.Call<ExchangeRateResponse>

    @GET("currency/take-rates-last-days")
    fun getExchangeRateForPastDays(@Query("amount") amount: Float,
                                   @Query("lastDays") lastDays: Int,
                                   @Query("sourceCurrencyType") sourceCurrency: String,
                                   @Query("targetCurrencyType") targetCurrency: String): retrofit2.Call<ExchangeRatePastDaysResponse>

    @Headers("Content-Type:application/json")
    @GET("user_profile/self_profile")
    fun getSelfProfileInformation(@Header("Authorization") token: String?): retrofit2.Call<SelfProfileInformationResponse>

    @Headers("Content-Type:application/json")
    @GET("user_profile/other_profile")
    fun getOtherProfileInformation(
        @Header("Authorization") token: String?,
        @Header("email") email: String?
    ): retrofit2.Call<OtherProfileInformationResponse>

    @Headers("Content-Type:application/json")
    @POST("user_profile/update_basic_profile")
    fun updateBasicUser(
        @Header("Authorization") token: String?,
        @Body info: BasicUserInformation
    ): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("user_profile/update_trader_profile")
    fun updateTraderUser(
        @Header("Authorization") token: String?,
        @Body info: TraderUserInformation
    ): retrofit2.Call<ResponseBody>

    @GET("article/article")
    fun getArticleById(@Query("id") articleId: String): retrofit2.Call<ArticleResponse>

    @GET("article/articles")
    fun getAllArticles(): retrofit2.Call<ArticlesListResponse>

    @GET("article/self_articles")
    fun getSelfArticles(@Header("Authorization") token: String?): retrofit2.Call<ArticlesListResponse>

    @GET("article/user_articles")
    fun getUserArticles(@Query("userEmail") email: String): retrofit2.Call<ArticlesListResponse>

    @POST("article/create")
    fun createArticle(@Header("Authorization") token: String?,
                      @Body info: ArticleInformation): retrofit2.Call<ArticleResponse>

    @POST("article/give_point")
    fun givePointToArticle(@Header("Authorization") token: String?,
                           @Query("articleID") articleId: String?,
                           @Query("score") score: Double): retrofit2.Call<ArticleResponse>

    @POST("article/update")
    fun updateArticle(@Header("Authorization") token: String?,
                      @Body info: ArticleInformation,
                      @Query("articleID") articleId: String): retrofit2.Call<ArticleResponse>

    @GET("event/get_events")
    fun getAllEvents(): retrofit2.Call<EventsListResponse>

    @GET("main_page/get_feed")
    fun getHomeFeed() : retrofit2.Call<HomeFeedResponse>

    @POST("user_following/follow")
    fun followUser(
        @Header("Authorization") token: String?,
        @Header("followingUserEmail") email: String?
    ): retrofit2.Call<MinimalUserResponse>


    // Requests related to comments
    @DELETE("comment_controller/delete_comment")
    fun deleteComment(
        @Header("Authorization") token: String?,
        @Query("articleOrEventId") articleOrEventId: String
    )

    @GET("comment_controller/get_comment")
    fun getComment(
        @Header("Authorization") token: String?,
        @Query("commentId") commentId: String
    ): retrofit2.Call<CommentResponse>

    @GET("comment_controller/get_comments_of_article")
    fun getAllCommentsOfArticleOrEvent(
        @Header("Authorization") token: String?,
        @Query("articleOrEventId") articleOrEventId: String
    ): retrofit2.Call<List<CommentResponse>>

    @GET("comment_controller/get_comments_of_user")
    fun getSelfComments(
        @Header("Authorization") token: String?
    ): retrofit2.Call<CommentListResponse>

    @POST("comment_controller/post_comment")
    fun addComment(
        @Header("Authorization") token: String?,
        @Body info: CommentInformation
    ): retrofit2.Call<CommentResponse>
}

data class BasicUserInformation(
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
    val city: String,
    val country: String,
    val locationX: Double,
    val locationY: Double
)

data class TraderUserInformation(
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
    val city: String,
    val country: String,
    val locationX: Double,
    val locationY: Double,
    val identityNo: String,
    val iban: String
)

data class LoginInformation(
    val email: String,
    val password: String
)

data class ArticleInformation(
    val authorEmail: String?,
    val authorName: String?,
    val authorSurname: String?,
    val content: String,
    val title: String
)

data class LoginResponse(
    val token: String
)

data class ExchangeRateResponse(
    val rate: Double
)

data class ExchangeRatePastDaysResponse(
    @SerializedName("currencyConverterResources")
    val pastExchangeRates: ArrayList<PastExchangeRateInfo>,
    val startDate: String

)

data class PastExchangeRateInfo(
    val amount: Double,  // actually we don't need this at all, can be removed later if it's possible
    val rate: Float,
    val date: String
)
data class SelfProfileInformationResponse(
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
    val city: String,
    val country: String,
    val locationX: Double,
    val locationY: Double,
    val identityNo: String,
    val iban: String,
    val role: String,
    val privacyType: String,
    val followerCount: Int,
    val followingCount: Int
)

data class OtherProfileInformationResponse(
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
    val city: String,
    val country: String,
    val locationX: Double,
    val locationY: Double,
    val identityNo: String,
    val iban: String,
    val role: String,
    val privacyType: String,
    val followerCount: Int,
    val followingCount: Int
)

data class ArticlesListResponse(
    @SerializedName("articles")
    val allArticles: ArrayList<ArticleResponse>
)

data class ArticleResponse(
    val authorEmail: String,
    val authorName: String,
    val authorSurname: String,
    val changeDate: String,
    val content: String,
    val score: Double,
    val title: String,
    val uuid: String

)

data class EventsListResponse(
    @SerializedName("instances")
    val allEvents: ArrayList<EventResponse>
)

data class EventResponse(
    val content: String,
    val guid: String,
    val link: String,
    val score: Double,
    val stringDate: String,
    val title: String
)

data class MinimalUserResponse(
    val email: String,
    val name: String,
    val surname: String,
    val id: String?
)

data class HomeFeedResponse(
    val suggestedUsers: ArrayList<MinimalUserResponse>,
    val suggestedEvents: ArrayList<EventResponse>,
    val suggestedArticles: ArrayList<ArticleResponse>

)

data class CommentResponse(
    val articleEventId: String,
    val commentType: String,
    val content: String,
    val createdDate: String,
    val id: String,
    @SerializedName("minimalUserResource")
    val userInfo: MinimalUserResponse
)

data class CommentListResponse(
    val comments: ArrayList<CommentResponse>
)

data class CommentInformation(
    val articleEventId: String,
    val commentType: String,
    val content: String,
    val title: String
)