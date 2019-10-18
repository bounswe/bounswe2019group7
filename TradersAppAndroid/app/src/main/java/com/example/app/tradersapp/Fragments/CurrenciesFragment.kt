package com.example.app.tradersapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_currencies.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.properties.Delegates

class CurrenciesFragment : Fragment() {

    private var rate: Double by Delegates.notNull<Double>()
    private var currency1 = "EUR"
    private var currency2 = "USD"
    private val currencies = listOf("TRY", "EUR", "USD", "GBP")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currencies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currencySpinner1 = currency1Spinner   // get reference to the spinners
        val currencySpinner2= currency2Spinner

        currencySpinner1.adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, currencies)
        currencySpinner2.adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, currencies)

        // Set default currencies and request exchange rate for those
        currencySpinner1.setSelection(1, false)
        currencySpinner2.setSelection(2, false)
        requestExchangeRateAndUpdateAmounts()

        swap.setOnClickListener {
            swapCurrencies()
            updateAmounts()
        }

        currencySpinner1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currency1 = currencySpinner1.getItemAtPosition(position).toString()
                requestExchangeRateAndUpdateAmounts()
            }

        }

        currencySpinner2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currency2 = currencySpinner2.getItemAtPosition(position).toString()
                requestExchangeRateAndUpdateAmounts()
            }

        }

        amount1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateAmounts()
            }
        })
    }

    private fun requestExchangeRateAndUpdateAmounts(){
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        retrofitService.getExchangeRate(currency1, currency2, 1.0).enqueue(object: Callback<ExchangeRateResponse>{
            override fun onFailure(call: Call<ExchangeRateResponse>, t: Throwable) {
                Toast.makeText(
                    activity,
                    "Unexpected server error occured. Please try again.",
                    Toast.LENGTH_SHORT
                ).show();
            }

            override fun onResponse(call: Call<ExchangeRateResponse>, response: Response<ExchangeRateResponse>) {
                rate = response.body()?.rate ?: 1.0
                updateAmounts()
            }
        })
    }

    private fun updateAmounts(){
        if(amount1.text.toString()!= ""){
            val exchangedValue = java.lang.Double.parseDouble(amount1.text.toString()) * rate
            amount2.text = BigDecimal(exchangedValue).setScale(2, RoundingMode.HALF_EVEN).toString()
        }
        else{
            amount2.text = "0"
        }
    }

    private fun swapCurrencies(){
        // Swap currencies
        val tempCurrency = currency1
        currency1 = currency2
        currency2 = tempCurrency

        // Reverse the exchange rate
        rate = 1 / rate

        // Swap background colors
        val tempBackground = layout1.background
        layout1.background = layout2.background
        layout2.background = tempBackground

        // Swap spinner selections
        val tempSelection = currency1Spinner.selectedItemPosition
        currency1Spinner.setSelection(currency2Spinner.selectedItemPosition)
        currency2Spinner.setSelection(tempSelection)

    }
}
