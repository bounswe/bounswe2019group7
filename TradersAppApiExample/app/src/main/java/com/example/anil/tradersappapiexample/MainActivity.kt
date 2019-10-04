package com.example.anil.tradersappapiexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ExampleApiInterface::class.java)
        //retrofitService.getIsOdd().enqueue(Callback<ApiResponse>())

    }
}

class RetrofitInstance {

    companion object {
        val BASE_URL: String = "OUR API URL"
        fun getRetrofitInstance(): Retrofit{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

}
interface ExampleApiInterface {
    @GET("OUR API ENDPOINT")
    fun getIsOdd(): retrofit2.Call<ApiResponse>
}

data class ApiResponse(
    val isOdd : Boolean
)