package com.example.app.tradersapp.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.app.tradersapp.*
import kotlinx.android.synthetic.main.fragment_funds.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FundsFragment : Fragment() {
    private var sp: SharedPreferences? = null
    private val retrofitService =
        RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EyeTradeUtils.showSpinner(activity)

        sp = PreferenceManager.getDefaultSharedPreferences(context)

        if (sp?.getString("token", null).isNullOrBlank()) {
            Toast.makeText(context, "Please login to view your funds", Toast.LENGTH_SHORT).show()
            (context as FragmentActivity).supportFragmentManager.popBackStack() //  go back
            return
        }

        getProfileInfo()

        createTradingAccountButton.setOnClickListener {
            createTradingAccount()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_funds, container, false)
    }

    private fun getProfileInfo() {
        retrofitService.getSelfProfileInformation(sp?.getString("token", null))
            .enqueue(object : Callback<SelfProfileInformationResponse> {
                override fun onFailure(
                    call: Call<SelfProfileInformationResponse>,
                    t: Throwable
                ) {
                    EyeTradeUtils.toastErrorMessage(activity!!.applicationContext, t)
                }

                override fun onResponse(
                    call: Call<SelfProfileInformationResponse>,
                    response: Response<SelfProfileInformationResponse>
                ) {
                    val body = response.body()
                    if (response.code() == 200) {
                        if (body?.role != "TRADER_USER") {
                            Toast.makeText(
                                context,
                                "You must be a trader user to view your funds",
                                Toast.LENGTH_SHORT
                            ).show()
                            (context as FragmentActivity).supportFragmentManager.popBackStack()
                            return
                        } else {
                            getTradingAccount()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Error getting profile information: " + response.code(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            })
    }

    private fun getTradingAccount() {
        retrofitService.getTradingAccount(sp?.getString("token", null))
            .enqueue(object : Callback<TradingAccountResponse> {
                override fun onFailure(call: Call<TradingAccountResponse>, t: Throwable) {
                    EyeTradeUtils.toastErrorMessage(activity!!.applicationContext, t)
                }

                override fun onResponse(
                    call: Call<TradingAccountResponse>,
                    response: Response<TradingAccountResponse>
                ) {
                    when {
                        response.code() == 200 -> { // we have a trading account
                            noAccountContainer.visibility = View.GONE
                            fundsContainer.visibility = View.VISIBLE
                            val body = response.body() ?: return
                            val allFunds = listOf(
                                FundsModel("TRY",body.tryAmount),
                                FundsModel("EUR",body.eurAmount),
                                FundsModel("USD",body.usdAmount),
                                FundsModel("GBP",body.gbpAmount),
                                FundsModel("JPY",body.jpyAmount),
                                FundsModel("CNY",body.cnyAmount),
                                FundsModel("BTC",body.bitcoinAmount),
                                FundsModel("ETH",body.ethereumAmount),
                                FundsModel("XRP",body.rippleAmount),
                                FundsModel("LTC",body.litecoinAmount),
                                FundsModel("XMR",body.moneroAmount)
                            )
                            fundsList.apply {
                                layoutManager = LinearLayoutManager(activity)
                                adapter = FundsAdapter(allFunds, context)
                                addItemDecoration(DividerItemDecoration(fundsList.context, LinearLayoutManager.VERTICAL))
                            }
                        }
                        response.code() == 404 -> { // no trading account
                            noAccountContainer.visibility = View.VISIBLE
                            fundsContainer.visibility = View.GONE
                        }
                        else -> Toast.makeText(
                            context,
                            "An error occurred while getting the trading account: " + response.code(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    EyeTradeUtils.hideSpinner(activity)
                }
            })
    }

    private fun createTradingAccount() {
        EyeTradeUtils.showSpinner(activity)
        retrofitService.createTradingAccount(sp?.getString("token", null))
            .enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    EyeTradeUtils.toastErrorMessage(activity!!.applicationContext, t)
                }

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    if (response.code() == 200) { // successfully created
                        getTradingAccount()
                    } else {
                        Toast.makeText(
                            context,
                            "An error occurred while creating the trading account: " + response.code(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }
}
