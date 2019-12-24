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
import kotlinx.android.synthetic.main.fragment_withdraw_funds.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        sp = PreferenceManager.getDefaultSharedPreferences(context)

        withdrawFundsSubmit.setOnClickListener {

            val fundAmount = withdrawFundAmount.text.toString().toDouble()
            val currency = withdrawFundTypeSpinner.selectedItem.toString()

            retrofitService.sellTransaction(
                sp?.getString("token", null),
                SellTransactionInformation(fundAmount, currency)
            )
                .enqueue(object : Callback<SellTransactionResponse> {
                    override fun onFailure(call: Call<SellTransactionResponse>, t: Throwable) {
                        EyeTradeUtils.toastErrorMessage(context!!, t)
                    }

                    override fun onResponse(
                        call: Call<SellTransactionResponse>,
                        response: Response<SellTransactionResponse>
                    ) {
                        (context as FragmentActivity).supportFragmentManager.popBackStack()
                    }
                })

        }

    }

}
