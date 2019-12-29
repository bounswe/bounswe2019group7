package com.example.app.tradersapp.Fragments


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.app.tradersapp.*
import kotlinx.android.synthetic.main.fragment_exchange_funds.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExchangeFundsFragment : Fragment() {

    private var sp: SharedPreferences? = null
    private val retrofitService =
        RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

    private var exchangeTypePosition1 = 0
    private var exchangeTypePosition2 = 1
    private var rate = 0.0

    private var editingText1 = false
    private var editingText2 = false

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

        exchangeFundTypeSpinner2.setSelection(1)

        updateExchangeRate()

        exchangeFundTypeSpinner1.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == exchangeFundTypeSpinner2.selectedItemPosition) {
                        exchangeFundTypeSpinner2.setSelection(exchangeTypePosition1)
                        exchangeTypePosition1 = position
                    }
                    updateExchangeRate()
                    updateAmount1()
                }
            }

        exchangeFundTypeSpinner2.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == exchangeFundTypeSpinner1.selectedItemPosition) {
                        exchangeFundTypeSpinner1.setSelection(exchangeTypePosition2)
                        exchangeTypePosition2 = position
                    }
                    updateExchangeRate()
                    updateAmount2()
                }
            }

        exchangeFundsSubmit.setOnClickListener {

            EyeTradeUtils.showSpinner(activity)

            val fundAmount = exchangeFundAmount1.text.toString().toDouble()
            val currency1 = exchangeFundTypeSpinner1.selectedItem.toString()
            val currency2 = exchangeFundTypeSpinner2.selectedItem.toString()

            retrofitService.exchangeTransaction(
                sp?.getString("token", null),
                ExchangeTransactionInformation(fundAmount, currency2, currency1)
            )
                .enqueue(object : Callback<ExchangeTransactionResponse> {
                    override fun onFailure(call: Call<ExchangeTransactionResponse>, t: Throwable) {
                        //EyeTradeUtils.toastErrorMessage(context!!, t)
                        Toast.makeText(context, "Not enough funds", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<ExchangeTransactionResponse>,
                        response: Response<ExchangeTransactionResponse>
                    ) {
                        (context as FragmentActivity).supportFragmentManager.popBackStack()
                    }
                })
        }

        exchangeFundAmount1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                editingText1 = false
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                editingText1 = true
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!editingText2) {
                    updateAmount2()
                }
            }
        })
        exchangeFundAmount2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                editingText2 = false
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                editingText2 = true
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!editingText1) {
                    updateAmount1()
                }
            }
        })
    }

    private fun updateExchangeRate() {
        EyeTradeUtils.showSpinner(activity)

        val exchangeType1 = exchangeFundTypeSpinner1.selectedItem.toString()
        val exchangeType2 = exchangeFundTypeSpinner2.selectedItem.toString()
        retrofitService.getExchangeRate(exchangeType1, exchangeType2)
            .enqueue(object : Callback<ExchangeRateResponse> {
                override fun onFailure(call: Call<ExchangeRateResponse>, t: Throwable) {
                    EyeTradeUtils.toastErrorMessage(context!!, t)
                }

                override fun onResponse(
                    call: Call<ExchangeRateResponse>,
                    response: Response<ExchangeRateResponse>
                ) {
                    if (response.code() == 200) {
                        val body = response.body()
                        if (body != null) rate = body.rate
                    }
                    EyeTradeUtils.hideSpinner(activity)
                }
            })
    }

    private fun updateAmount1(){
        val amount2Str = exchangeFundAmount2.text.toString()
        val amount2 = if(amount2Str.isBlank()) 0.0 else amount2Str.toDouble()
        val newAmount1 = amount2 * rate
        exchangeFundAmount1.setText("%.5f".format(newAmount1))
    }

    private fun updateAmount2(){
        val amount1Str = exchangeFundAmount1.text.toString()
        val amount1 = if(amount1Str.isBlank()) 0.0 else amount1Str.toDouble()
        val newAmount2 = amount1 * rate
        exchangeFundAmount2.setText("%.5f".format(newAmount2))
    }

}
