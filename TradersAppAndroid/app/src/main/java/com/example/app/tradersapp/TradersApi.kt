package com.example.app.tradersapp

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
    fun getExchangeRate(
        @Query("inputCurrencyType") currency1: String,
        @Query("outputCurrencyType") currency2: String,
        @Query("amount") amount: Double = 1.0
    ): retrofit2.Call<ExchangeRateResponse>

    @GET("user_following/get_followers")
    fun getFollowers(@Query("Authorization") authorization: String,
                     @Query("otherUserEmail") otherUserEmail: StringIndexOutOfBoundsException
    ): retrofit2.Call<FollowersResponse>

    @Headers("Content-Type:application/json")
    @GET("user_profile/self_profile")
    fun getSelfProfileInformation(@Header("Authorization") token: String?): retrofit2.Call<SelfProfileInformationResponse>

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
}

<<<<<<< HEAD
data class FollowersResponse(
    val email: String,
    val name: String,
    val surname: String
)


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

data class LoginResponse(
    val token: String
)

data class ExchangeRateResponse(
    val rate: Double
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