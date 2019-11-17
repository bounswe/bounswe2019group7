package com.example.app.tradersapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private var sp:SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sp?.edit()
        editor?.putString("token", "")
        editor?.apply()

        loginButton.setOnClickListener {

            val email = email.text.toString()
            val password = password.text.toString()
            validateAndLogin(email, password)

        }

        registerButton.setOnClickListener {
            openRegistrationPage()
        }

        continueAsGuestButton.setOnClickListener {
            openHomePage()
        }
    }

    private fun validateAndLogin(email: String, password: String) {
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val loginInfo = LoginInformation(email, password)

        retrofitService.loginUser(loginInfo).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.i("ApiRequest", "Request failed: " + t.toString())
                Toast.makeText(
                    this@MainActivity,
                    "Unexpected server error occurred. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.code() == 200) {
                    val editor = sp?.edit()
                    editor?.putString("token", response.body()?.token)
                    editor?.apply()
                    openHomePage()
                } else {
                    Toast.makeText(this@MainActivity, "Login failed.", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun openHomePage() {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openRegistrationPage() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }
}

