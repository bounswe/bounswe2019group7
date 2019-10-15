package com.example.app.tradersapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class RetrofitInstance {

    companion object {
        val BASE_URL: String = "http://100.26.202.213:8080/"
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}

interface ApiInterface {
    @FormUrlEncoded
    @POST("user/register")
    fun registerUser(
        @Field("name") name: String,
        @Field("surname") surname: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("role") userType: String,
        @Field("city") city: String = "Istanbul",
        @Field("iban") iban: String = "56451465465",
        @Field("identityNo") identityNo: String = "564654",
        @Field("locationX") locationX: String = "10",
        @Field("locationY") locationY: String = "40"
    ): retrofit2.Call<ApiResponse>
}

data class ApiResponse(
    val isOdd: Boolean // TODO: Clarify the response type with backend
)