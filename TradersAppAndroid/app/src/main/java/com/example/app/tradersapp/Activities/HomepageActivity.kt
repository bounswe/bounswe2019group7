package com.example.app.tradersapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_homepage.*

class HomepageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        button.setOnClickListener {
            val currenciesFragment = CurrenciesFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerLayout, currenciesFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}
