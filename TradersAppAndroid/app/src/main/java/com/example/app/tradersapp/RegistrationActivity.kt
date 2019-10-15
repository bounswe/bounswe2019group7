package com.example.app.tradersapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registerCancelButton.setOnClickListener {
            this.finish()
        }

        registerSignUpButton.setOnClickListener {
            Toast.makeText(this, "Signing Up", Toast.LENGTH_SHORT).show();
            val name = registerNameInput.text.toString()
            val surname = registerSurnameInput.text.toString()
            val password = registerPasswordInput.text.toString()
            val passwordAgain = registerPasswordAgainInput.text.toString()
            val location = registerLocationInput.text.toString()
            val email = registerEmailInput.text.toString()
            val userTypeId = userTypeRadioGroup.checkedRadioButtonId

            val userType = if (userTypeId == basicUserRadio.id) "BASIC_USER" else "TRADER_USER"

            val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

            retrofitService.registerUser(name, surname, password, email, userType).enqueue(object :
                Callback<ApiResponse> {
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.i("ApiRequest", "Request failed: " + t.toString())
                }

                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {

                }

            })


        }
    }
}
