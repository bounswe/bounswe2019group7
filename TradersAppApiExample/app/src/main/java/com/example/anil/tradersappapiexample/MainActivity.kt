package com.example.anil.tradersappapiexample

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
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ExampleApiInterface::class.java)

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
        val intent = Intent()
        // TODO: Go to home page after implementing it
    }

    private fun openRegistrationPage(){
        val intent = Intent()
        // TODO: Go to registration page after implementing it
    }
}

