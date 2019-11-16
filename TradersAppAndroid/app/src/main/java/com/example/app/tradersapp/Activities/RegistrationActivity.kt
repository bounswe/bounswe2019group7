package com.example.app.tradersapp

import android.app.Activity
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

const val LOCATION_PICKER_CODE = 0

class RegistrationActivity : AppCompatActivity() {

    private var updateProfile: Boolean = false

    private var sp: SharedPreferences? = null

    private var city: String = ""
    private var country: String = ""
    private var locationX: Double = 0.0
    private var locationY: Double = 0.0
    private var locationSet: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        sp = PreferenceManager.getDefaultSharedPreferences(this)

        updateProfile = intent.extras?.get("updateProfile") == true

        if (updateProfile) {
            registerSignUpButton.text = "Update"

            val retrofitService =
                RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

            retrofitService.getSelfProfileInformation(sp?.getString("token", null))
                .enqueue(object : Callback<SelfProfileInformationResponse> {
                    override fun onFailure(
                        call: Call<SelfProfileInformationResponse>,
                        t: Throwable
                    ) {
                        Log.i("ApiRequest", "Request failed: " + t.toString())
                        Toast.makeText(
                            this@RegistrationActivity,
                            "Unexpected server error occurred. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<SelfProfileInformationResponse>,
                        response: Response<SelfProfileInformationResponse>
                    ) {
                        if (response.code() == 200) {
                            val body = response.body()
                            if (body != null) {
                                registerNameInput.setText(body.name)
                                registerSurnameInput.setText(body.surname)
                                registerEmailInput.setText(body.email)
                                registerIdNoInput.setText(body.identityNo)
                                registerIBANInput.setText(body.iban)
                                locationX = body.locationX
                                locationY = body.locationY
                                city = body.city
                                country = body.country
                                registerLocationButton.text = if(city.isBlank()) country else "$city, $country"
                                locationSet = true
                                if (body.role == "BASIC_USER") {
                                    basicUserRadio.isSelected = true
                                } else {
                                    tradingUserRadio.isSelected = true
                                }
                                displayTraderUserInput()
                            }
                        } else {
                            Toast.makeText(
                                this@RegistrationActivity,
                                "Not logged in.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                })
        }

        registerCancelButton.setOnClickListener {
            this.finish()
        }

        registerSignUpButton.setOnClickListener {
            if (!locationSet) {
                Toast.makeText(this, "Please pick your location", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val password = registerPasswordInput.text.toString()
            val passwordAgain = registerPasswordAgainInput.text.toString()
            if (password != passwordAgain) {
                Toast.makeText(this, "Please enter the same password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val name = registerNameInput.text.toString()
            val surname = registerSurnameInput.text.toString()
            val email = registerEmailInput.text.toString()
            val userTypeId = userTypeRadioGroup.checkedRadioButtonId

            val userType = if (userTypeId == basicUserRadio.id) "BASIC_USER" else "TRADER_USER"

            var iban = registerIBANInput.text.toString()
            var idNo = registerIdNoInput.text.toString()

            val retrofitService =
                RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

            if (userType == "BASIC_USER") {

                // TODO: Add Phone Number
                val registrationInfo =
                    BasicUserInformation(
                        name,
                        surname,
                        "05555555555",
                        email,
                        city,
                        country,
                        locationX,
                        locationY
                    )

                Toast.makeText(
                    this,
                    if (updateProfile) "Updating Profile" else "Signing Up",
                    Toast.LENGTH_SHORT
                ).show()

                if (!updateProfile) {
                    retrofitService.registerBasicUser(registrationInfo, password)
                        .enqueue(RegisterCallback.apply {
                            this.activity = this@RegistrationActivity
                        })
                } else {
                    retrofitService.updateBasicUser(sp?.getString("token", null), registrationInfo)
                        .enqueue(UpdateCallback.apply { this.activity = this@RegistrationActivity })
                }
            } else if (userType == "TRADER_USER") {
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
                // TODO: Add Phone Number
                val registrationInfo =
                    TraderUserInformation(
                        name,
                        surname,
                        "05555555555",
                        email,
                        city,
                        country,
                        locationX,
                        locationY,
                        idNo,
                        iban
                    )

                Toast.makeText(
                    this,
                    if (updateProfile) "Updating Profile" else "Signing Up",
                    Toast.LENGTH_SHORT
                ).show()

                if (!updateProfile) {
                    retrofitService.registerTraderUser(registrationInfo, password)
                        .enqueue(RegisterCallback.apply {
                            this.activity = this@RegistrationActivity
                        })
                } else {
                    retrofitService.updateTraderUser(sp?.getString("token", null), registrationInfo)
                        .enqueue(UpdateCallback.apply { this.activity = this@RegistrationActivity })
                }
            }
        }

        userTypeRadioGroup.setOnCheckedChangeListener { _: RadioGroup, _: Int ->
            displayTraderUserInput()
        }
        displayTraderUserInput()

        registerLocationButton.setOnClickListener {
            val intent = Intent(this, LocationPickerActivity::class.java)
            startActivityForResult(intent, LOCATION_PICKER_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LOCATION_PICKER_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    city = data.getStringExtra("city")
                    country = data.getStringExtra("country")
                    locationX = data.getDoubleExtra("locationX", 0.0)
                    locationY = data.getDoubleExtra("locationY", 0.0)
                    registerLocationButton.text = data.getStringExtra("name")
                    locationSet = true
                }
            }
        }
    }

    private fun displayTraderUserInput() {
        val vis =
            if (userTypeRadioGroup.checkedRadioButtonId == basicUserRadio.id) View.GONE else View.VISIBLE
        registerIBANInput.visibility = vis
        registerIdNoInput.visibility = vis
    }

    object RegisterCallback :
        Callback<ResponseBody> {
        var activity: RegistrationActivity? = null

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.i("ApiRequest", "Request failed: " + t.toString())
            Toast.makeText(
                activity,
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
                    activity,
                    "Successfully registered!",
                    Toast.LENGTH_SHORT
                )
                    .show()
                val intent =
                    Intent(activity, MainActivity::class.java)
                activity?.startActivity(intent)
                activity?.finish()
            } else {
                Toast.makeText(
                    activity,
                    "Registration failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    object UpdateCallback :
        Callback<ResponseBody> {
        var activity: RegistrationActivity? = null

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            Log.i("ApiRequest", "Request failed: " + t.toString())
            Toast.makeText(
                activity,
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
                    activity,
                    "Profile updated!",
                    Toast.LENGTH_SHORT
                ).show()
                activity?.finish()
            } else {
                Toast.makeText(
                    activity,
                    "Could not update profile: " + response.code(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
