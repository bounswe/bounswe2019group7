package com.example.app.tradersapp.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.app.tradersapp.CurrenciesAdapter
import com.example.app.tradersapp.CurrencyEntry

import com.example.app.tradersapp.R
import kotlinx.android.synthetic.main.fragment_currencies.*


class CurrenciesFragment : Fragment() {

    private var baseCurrency = "TRY" // TRY is selected by default
    private var prevCheckedId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currencies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var currencyEntries = ArrayList<CurrencyEntry>()
        currencyEntries.add(CurrencyEntry("EUR", R.drawable.trading_logo))
        currencyEntries.add(CurrencyEntry("GBP", R.drawable.trading_logo))
        currencyEntries.add(CurrencyEntry("USD", R.drawable.trading_logo))
        currencyEntries.add(CurrencyEntry("TRY", R.drawable.trading_logo))

        rwCurrencies.layoutManager = LinearLayoutManager(context)
        rwCurrencies.setHasFixedSize(true) //improves performance
        rwCurrencies.adapter = context?.let { CurrenciesAdapter(currencyEntries, it) }


        prevCheckedId = baseCurrencyRadioGroup.checkedRadioButtonId

        baseCurrencyRadioGroup.setOnCheckedChangeListener { group, checkedId ->

            val checkedButton = group.findViewById<RadioButton>(checkedId)
            updateButtonColors(prevCheckedId, checkedButton)
            prevCheckedId = checkedId
            baseCurrency = checkedButton.text.toString()
            //updateCurrencies()

        }
    }

    private fun updateButtonColors(previousCheckedId: Int, newlyCheckedButton: RadioButton) {
        newlyCheckedButton.background = (resources.getDrawable(R.drawable.round_button, null))
        baseCurrencyRadioGroup.findViewById<RadioButton>(previousCheckedId).background = null
    }

}
