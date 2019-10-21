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
    @POST("registration/register")
    fun registerUser(
        @Body info: RegistrationInformation
    ): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("login")
    fun loginUser(@Body info: LoginInformation): retrofit2.Call<ResponseBody>


    @GET("currency/convert")
    fun getExchangeRate(@Query("inputCurrencyType") currency1: String,
                        @Query("outputCurrencyType") currency2: String,
                        @Query("amount") amount: Double = 1.0): retrofit2.Call<ExchangeRateResponse>


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
    val iban: String,
    val identityNo: String
)

data class LoginInformation(
    val email: String,
    val password: String
)

data class ExchangeRateResponse(
    val rate: Double
)