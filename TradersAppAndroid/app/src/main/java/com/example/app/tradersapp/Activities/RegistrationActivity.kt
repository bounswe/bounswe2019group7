package com.example.app.tradersapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*
import okhttp3.ResponseBody
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
            val password = registerPasswordInput.text.toString()
            val passwordAgain = registerPasswordAgainInput.text.toString()
            if (password != passwordAgain) {
                Toast.makeText(this, "Enter the same password please!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Signing Up", Toast.LENGTH_SHORT).show();
                val name = registerNameInput.text.toString()
                val surname = registerSurnameInput.text.toString()
                val location = registerLocationInput.text.toString()
                val email = registerEmailInput.text.toString()
                val userTypeId = userTypeRadioGroup.checkedRadioButtonId

                val userType = if (userTypeId == basicUserRadio.id) "BASIC_USER" else "TRADER_USER"

                val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

                // TODO: Check the initialization below, 3 locations are passed at the end, may need to change later!
                val registrationInfo =
                    RegistrationInformation(name, surname, email, password, userType, location, location, location)

                retrofitService.registerUser(registrationInfo).enqueue(object :
                    Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.i("ApiRequest", "Request failed: " + t.toString())
                        Toast.makeText(
                            this@RegistrationActivity,
                            "Unexpected server error occured. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show();
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.code() == 200) {
                            Toast.makeText(this@RegistrationActivity, "Successfully registered!", Toast.LENGTH_SHORT)
                                .show();
                            val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@RegistrationActivity, "Registration failed.", Toast.LENGTH_SHORT)
                                .show();
                        }
                    }
                })
            }
        }
    }
}
