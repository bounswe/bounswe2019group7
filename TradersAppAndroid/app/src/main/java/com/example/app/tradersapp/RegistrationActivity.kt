package com.example.app.tradersapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registerCancelButton.setOnClickListener {
            this.finish()
        }

        registerSignUpButton.setOnClickListener {
            Toast.makeText(this, "Signing Up", Toast.LENGTH_SHORT).show();
        }
    }
}
