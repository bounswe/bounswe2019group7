package com.example.app.tradersapp

import android.content.Context
import android.util.Log
import android.widget.Toast

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
    }
}