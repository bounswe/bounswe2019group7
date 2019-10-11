package com.example.app.tradersapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

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

interface ExampleApiInterface {
    @GET("api/example")
    fun getIsOdd(@Header("number") number: Int): retrofit2.Call<ApiResponse>
}

data class ApiResponse(
    val isOdd: Boolean
)