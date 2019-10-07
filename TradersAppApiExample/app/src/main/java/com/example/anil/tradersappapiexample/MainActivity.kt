package com.example.anil.tradersappapiexample

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ExampleApiInterface::class.java)
        retrofitService.getIsOdd(1).enqueue(object : Callback<ApiResponse> {

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("ErrorTag", t.cause.toString())
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val response = response.body()
                response?.let {
                    if (it.isOdd) {
                        findViewById<View>(R.id.rootLayout).setBackgroundColor(Color.YELLOW)
                        findViewById<TextView>(R.id.responseText).text = "The response is odd."
                    } else {
                        findViewById<View>(R.id.rootLayout).setBackgroundColor(Color.CYAN)
                        findViewById<TextView>(R.id.responseText).text = "The response is even."
                    }
                }

            }

        })

    }
}

