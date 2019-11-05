package com.example.app.tradersapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {

    private var updateProfile: Boolean = false

    private var sp:SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        sp = PreferenceManager.getDefaultSharedPreferences(this)

        updateProfile = intent.extras?.get("updateProfile") == true

        if(updateProfile){
            registerSignUpButton.text="Update"
        }

        registerCancelButton.setOnClickListener {
            this.finish()
        }

        registerSignUpButton.setOnClickListener {
            val password = registerPasswordInput.text.toString()
            val passwordAgain = registerPasswordAgainInput.text.toString()
            if (password != passwordAgain) {
                Toast.makeText(this, "Please enter the same password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val name = registerNameInput.text.toString()
            val surname = registerSurnameInput.text.toString()
            val location = registerLocationInput.text.toString()
            val email = registerEmailInput.text.toString()
            val userTypeId = userTypeRadioGroup.checkedRadioButtonId

            val userType = if (userTypeId == basicUserRadio.id) "BASIC_USER" else "TRADER_USER"

            var iban = registerIBANInput.text.toString()
            var idNo = registerIdNoInput.text.toString()
            if (userType == "TRADER_USER") {
                // length of iban has to be between 16 and 18
                if (iban.length < 16 || iban.length > 18) {
                    Toast.makeText(this, "IBAN must have 16-18 characters!", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                // length of idno has to be 11
                if (idNo.length != 11) {
                    Toast.makeText(this, "Identity No must have 11 characters!", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
            } else {
                iban = "000000000000000000"
                idNo = "00000000000"
            }

            val retrofitService =
                RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

            // TODO: Check the initialization below, 3 locations are passed at the end, may need to change later!
            val registrationInfo =
                RegistrationInformation(
                    name,
                    surname,
                    email,
                    password,
                    userType,
                    location,
                    location,
                    location,
                    iban,
                    idNo
                )

            Toast.makeText(this, if(updateProfile) "Updating Profile" else "Signing Up", Toast.LENGTH_SHORT).show()

            if (!updateProfile) {
                retrofitService.registerUser(registrationInfo).enqueue(object :
                    Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.i("ApiRequest", "Request failed: " + t.toString())
                        Toast.makeText(
                            this@RegistrationActivity,
                            "Unexpected server error occurred. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.code() == 200) {
                            Toast.makeText(
                                this@RegistrationActivity,
                                "Successfully registered!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@RegistrationActivity,
                                "Registration failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            } else {
                retrofitService.updateUser(sp?.getString("token",null),registrationInfo).enqueue(object :
                    Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.i("ApiRequest", "Request failed: " + t.toString())
                        Toast.makeText(
                            this@RegistrationActivity,
                            "Unexpected server error occurred. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.code() == 200) {
                            Toast.makeText(
                                this@RegistrationActivity,
                                "Profile updated!",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@RegistrationActivity,
                                "Could not update profile: "+response.code(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }
        }

        userTypeRadioGroup.setOnCheckedChangeListener { _: RadioGroup, _: Int ->
            displayTraderUserInput()
        }
        displayTraderUserInput()
    }

    private fun displayTraderUserInput() {
        val vis =
            if (userTypeRadioGroup.checkedRadioButtonId == basicUserRadio.id) View.GONE else View.VISIBLE
        registerIBANInput.visibility = vis
        registerIdNoInput.visibility = vis
    }
}
