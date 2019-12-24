package com.example.app.tradersapp.Fragments


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.tradersapp.*
import kotlinx.android.synthetic.main.fragment_exchange_funds.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExchangeFundsFragment : Fragment() {

    private var sp: SharedPreferences? = null
    private val retrofitService =
        RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exchange_funds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = PreferenceManager.getDefaultSharedPreferences(context)

        exchangeFundsSubmit.setOnClickListener {

            val fundAmount = exchangeFundAmount1.text.toString().toDouble()
            val currency1 = exchangeFundTypeSpinner1.selectedItem.toString()
            val currency2 = exchangeFundTypeSpinner2.selectedItem.toString()

            retrofitService.exchangeTransaction(
                sp?.getString("token", null),
                ExchangeTransactionInformation(fundAmount, currency1, currency2)
            )
                .enqueue(object : Callback<ExchangeTransactionResponse> {
                    override fun onFailure(call: Call<ExchangeTransactionResponse>, t: Throwable) {
                        EyeTradeUtils.toastErrorMessage(context!!, t)
                    }

                    override fun onResponse(
                        call: Call<ExchangeTransactionResponse>,
                        response: Response<ExchangeTransactionResponse>
                    ) {
                        (context as FragmentActivity).supportFragmentManager.popBackStack()
                    }
                })

        }

    }

}
