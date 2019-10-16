package com.example.app.tradersapp

import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


class RetrofitInstance {
    companion object {
        val BASE_URL: String = "http://100.26.202.213:8080/"


        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client : OkHttpClient = OkHttpClient.Builder().apply {
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
    @POST("registration/register")
    fun registerUser(
        @Body info: RegistrationInformation
    ): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("login")
    fun loginUser(
        @Body info: LoginInformation
    ): retrofit2.Call<ResponseBody>
}

data class RegistrationInformation(
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val role: String,
    val locationY: String,
    val locationX: String,
    val city: String,
    val iban: String = "5645146555895645",  // length of iban has to be between 16 and 18
    val identityNo: String = "54848458964"  // length of identity has to be 11
)

data class LoginInformation(
    val email: String,
    val password: String
)