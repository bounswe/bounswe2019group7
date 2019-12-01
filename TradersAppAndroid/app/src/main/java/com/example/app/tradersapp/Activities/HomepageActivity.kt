package com.example.app.tradersapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import com.example.app.tradersapp.Fragments.*

import kotlinx.android.synthetic.main.activity_homepage.*
import java.util.*


class HomepageActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        actionBar?.title = "eyeTrade"

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        ) {}


        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()


        // Set navigation view navigation item selected listener
        nav_view.setNavigationItemSelectedListener {
            var fragment: Fragment? = null

            when (it.itemId) {
                R.id.action_home -> {
                    fragment = HomeFragment()
                }
                R.id.action_currencies -> {
                    fragment = CurrenciesFragment()
                }

                R.id.action_currency_converter -> {
                    fragment = CurrencyConverterFragment()
                }

                R.id.action_articles -> {
                    fragment = ArticleBaseFragment.newInstance()
                }

                R.id.action_events -> {
                    fragment = EventFragment.newInstance()

                }

                R.id.action_profile -> {
                    fragment = ProfileFragment()
                }

            }

            if (fragment != null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            // Close the drawer
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }

        // Default fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, HomeFragment())
        transaction.commit()
    }

    override fun onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
            return
        }
        super.onBackPressed()
    }

}
