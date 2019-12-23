package com.example.app.tradersapp

import android.app.Notification
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.app.tradersapp.Activities.NotificationActivity
import com.google.firebase.messaging.RemoteMessage
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val CHANNEL_ID = "personal_notifications"
const val NOTIFICATION_ID = 123
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

    public fun displayNotification() {
        val newMessageNotification = Notification.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("One new follower")
            .setContentText("user x followed you")
            .setPriority(Notification.PRIORITY_DEFAULT)
            .build()

        with(NotificationManagerCompat.from(this)) {
            notificationManager.notify(NOTIFICATION_ID, newMessageNotification)
        }



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

    private fun openNotificationsPage() {
        val intent = Intent(this, NotificationActivity::class.java)
        startActivity(intent)
    }
}

