package com.example.app.tradersapp

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_homepage.*

class EyeTradeUtils {
    companion object {

        fun toastErrorMessage(context: Context, t: Throwable){
            Log.i("ApiRequest", "Request failed: " + t.toString())
            Toast.makeText(
                context,
                "Unexpected server error occurred. Please try again.",
                Toast.LENGTH_SHORT
            ).show()
        }

        fun showSpinner(activity: FragmentActivity?){
            activity?.spinner?.visibility = View.VISIBLE
            activity?.nav_host_fragment?.visibility = View.GONE
        }

        fun hideSpinner(activity: FragmentActivity?){
            activity?.spinner?.visibility = View.GONE
            activity?.nav_host_fragment?.visibility = View.VISIBLE
        }

    }
}