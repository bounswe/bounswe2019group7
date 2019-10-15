package com.example.app.tradersapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)    // Remove title from the toolbar

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

        loginButton.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            if (!validateLoginCredentials(email, password)) {
                /* AlertDialog.Builder(applicationContext)
                     .setTitle("Login unsuccessful.")
                     .setMessage("Enter your information again...")
                     .show() */
            }
            else {
                openHomePage()
            }
        }

        registerButton.setOnClickListener {
            openRegistrationPage()

        }
    }

    private fun validateLoginCredentials(email: String, password: String): Boolean {
        return true
    }

    private fun openHomePage(){
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
    }

    private fun openRegistrationPage(){
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)

    }
}

