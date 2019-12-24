package com.example.app.tradersapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.tradersapp.EyeTradeUtils

import com.example.app.tradersapp.R

class FundsFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EyeTradeUtils.showSpinner(activity)
        getTradingAccount()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_funds, container, false)
    }

    private fun getTradingAccount(){

    }
}
