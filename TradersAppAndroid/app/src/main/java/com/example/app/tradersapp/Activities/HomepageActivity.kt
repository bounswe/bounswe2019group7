package com.example.app.tradersapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.ActionBarDrawerToggle
import com.example.app.tradersapp.Fragments.EventArticleFragment
import com.example.app.tradersapp.Fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_homepage.*



class HomepageActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        actionBar?.title = "eyeTrade - Home"

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
                    changeTitle("Home")
                }
                R.id.action_currencies -> {
                    fragment = CurrenciesFragment()
                    changeTitle("Currencies")
                }

                R.id.action_articles -> {
                    fragment = EventArticleFragment.newInstance(true)
                    changeTitle("Economic Articles")
                }

                R.id.action_events -> {
                    fragment = EventArticleFragment.newInstance(false)
                    changeTitle("Economic Events")
                }


            }

            if (fragment != null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment, fragment)
                transaction.commit()
            }

            // Close the drawer
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun changeTitle(s: String) {
        actionBar?.title = "eyeTrade - $s"
    }

}
