package com.example.app.tradersapp.Fragments


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.tradersapp.ApiInterface
import com.example.app.tradersapp.EyeTradeUtils

import com.example.app.tradersapp.R
import com.example.app.tradersapp.RetrofitInstance

class WithdrawFundsFragment : Fragment() {

    private var sp: SharedPreferences? = null
    private val retrofitService =
        RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_withdraw_funds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EyeTradeUtils.showSpinner(activity)

        sp = PreferenceManager.getDefaultSharedPreferences(context)
    }

}
