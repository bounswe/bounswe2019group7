package com.example.app.tradersapp.Fragments


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.app.tradersapp.*

import kotlinx.android.synthetic.main.fragment_add_funds.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFundsFragment : Fragment() {


    private var sp: SharedPreferences? = null
    private val retrofitService =
        RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_funds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = PreferenceManager.getDefaultSharedPreferences(context)

        addFundsSubmit.setOnClickListener {

            val fundAmount = addFundAmount.text.toString().toDouble()
            val currency = addFundTypeSpinner.selectedItem.toString()
            val creditCardNumber = addFundsCreditCardNumber.text.toString()
            val expiryDate: String = addFundsExpiryDate.text.toString()
            val cvv: String = addFundsCVV.text.toString()

            retrofitService.buyTransaction(
                sp?.getString("token", null),
                BuyTransactionInformation(fundAmount, currency, creditCardNumber, expiryDate, cvv)
            )
                .enqueue(object : Callback<BuyTransactionResponse> {
                    override fun onFailure(call: Call<BuyTransactionResponse>, t: Throwable) {
                        EyeTradeUtils.toastErrorMessage(context!!, t)
                    }

                    override fun onResponse(
                        call: Call<BuyTransactionResponse>,
                        response: Response<BuyTransactionResponse>
                    ) {
                        (context as FragmentActivity).supportFragmentManager.popBackStack()
                    }
                })

        }

    }

}
