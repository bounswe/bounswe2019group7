package com.example.app.tradersapp.Fragments


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.app.tradersapp.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_currencies.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal
import java.math.RoundingMode


class CurrenciesFragment : Fragment() {

    private var baseCurrency = "TRY"    // TRY is the base currency by default
    private var targetCurrency = "EUR"  // EUR is the target currency by default
    private var prevBaseId = 0
    private var prevTargetId = 0

    private lateinit var mChart: LineChart
    private var mPastExchangeRates: MutableList<PastExchangeRateInfo>? = null
    private var mPlotEntries: List<Entry>? = null

    private var currencyIdPairs:Array<Pair<RadioButton,RadioButton>> = arrayOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currencies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencyIdPairs =
            arrayOf(
                Pair(tryRb, tryRb2),
                Pair(eurRb, eurRb2),
                Pair(usdRb, usdRb2),
                Pair(gbpRb, gbpRb2),
                Pair(jpyRb, jpyRb2),
                Pair(cnyRb, cnyRb2)
            )


        prevBaseId = baseCurrencyRadioGroup.checkedRadioButtonId
        prevTargetId = targetCurrencyRadioGroup.checkedRadioButtonId

        requestPastRates(7, baseCurrency, targetCurrency)

        baseCurrencyRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedButton = group.findViewById<RadioButton>(checkedId)
            if(!checkedButton.isPressed) return@setOnCheckedChangeListener
            if(checkedButton.text != targetCurrency){
                updateButtonColors(prevBaseId, checkedButton,baseCurrencyRadioGroup)
                prevBaseId = checkedId
                baseCurrency = checkedButton.text.toString()
                requestPastRates(7, baseCurrency, targetCurrency)
            }else{
                swapBaseTarget()
            }
        }

        targetCurrencyRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedButton = group.findViewById<RadioButton>(checkedId)
            if(!checkedButton.isPressed) return@setOnCheckedChangeListener
            if(checkedButton.text != baseCurrency){
                updateButtonColors(prevTargetId, checkedButton,targetCurrencyRadioGroup)
                prevTargetId = checkedId
                targetCurrency = checkedButton.text.toString()
                requestPastRates(7, baseCurrency, targetCurrency)   // Requesting for last 7 days right now
            }else{
                swapBaseTarget()
            }
        }
    }

    private fun swapBaseTarget(){
        val basePair = currencyIdPairs.find { it.first.id == prevBaseId }
        val targetPair = currencyIdPairs.find { it.second.id == prevTargetId }
        val newBaseRb = targetPair!!.first
        val newTargetRb = basePair!!.second

        updateButtonColors(prevBaseId, newBaseRb, baseCurrencyRadioGroup)
        updateButtonColors(prevTargetId, newTargetRb, targetCurrencyRadioGroup)
        prevBaseId = newBaseRb.id
        prevTargetId = newTargetRb.id
        baseCurrency = newBaseRb.text.toString()
        targetCurrency = newTargetRb.text.toString()

        newBaseRb.isChecked = true
        newTargetRb.isChecked = true
        requestPastRates(7, baseCurrency, targetCurrency)
    }

    private fun updateButtonColors(previousCheckedId: Int, newlyCheckedButton: RadioButton, radioGroup: RadioGroup) {
        newlyCheckedButton.background = (resources.getDrawable(R.drawable.round_button, null))
        radioGroup.findViewById<RadioButton>(previousCheckedId).background = null
    }

    private fun requestPastRates(lastdays: Int, sourceCurrency: String, targetCurrency: String){

        EyeTradeUtils.showSpinner(activity)
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        retrofitService.getExchangeRateForPastDays(1f, lastdays, sourceCurrency, targetCurrency).enqueue(object: Callback<ExchangeRatePastDaysResponse>{
            override fun onFailure(call: Call<ExchangeRatePastDaysResponse>, t: Throwable) {
                Toast.makeText(
                    activity,
                    "Unexpected server error occurred. Please try again.",
                    Toast.LENGTH_SHORT
                ).show();
            }

            override fun onResponse(
                call: Call<ExchangeRatePastDaysResponse>,
                response: Response<ExchangeRatePastDaysResponse>
            ) {
                // If there is a response, update the list, else, keep it as it is
                mPastExchangeRates = response.body()?.pastExchangeRates ?: mPastExchangeRates
                mPlotEntries = mPastExchangeRates?.mapIndexed { index, pastExchangeRateInfo ->
                    Entry(index.toFloat(), pastExchangeRateInfo.rate)
                }
                if(!::mChart.isInitialized){
                    createPlot()
                }
                updatePlot()
                EyeTradeUtils.hideSpinner(activity)
            }
        })
    }

    private fun createPlot(){
        mChart = chart
        mChart.apply{
            setTouchEnabled(true)
            setPinchZoom(true)
            description.isEnabled = false
            xAxis.isEnabled = false
            axisRight.isEnabled = false
            legend.textColor = Color.WHITE
            description.textColor = Color.WHITE
            axisLeft.textColor = Color.WHITE
            axisLeft.textSize = 12f
            //description.text = "Change in last 7 days"
            //description.textSize = 16f
            legend.textSize = 12f
            //description.setPosition(1f, 0f)
        }
    }

    private fun updatePlot(){
        val lineDataSet1 = LineDataSet(mPlotEntries, "$baseCurrency - $targetCurrency")
        lineDataSet1.apply{
            color = Color.RED
            setDrawValues(true)
            axisDependency = YAxis.AxisDependency.LEFT
            setDrawIcons(false)
            //enableDashedLine(10f, 5f, 0f)
            //enableDashedHighlightLine(10f, 5f, 0f)
            color = Color.WHITE
            setCircleColor(Color.WHITE)
            lineWidth = 2f
            circleRadius = 3f
            setDrawCircleHole(false)
            valueTextSize = 16f
            setDrawFilled(true)
            //formLineWidth = 1f
            //formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            //formSize = 15f
            fillColor = Color.WHITE
            setValueTextColors(mutableListOf(Color.WHITE))
        }

        val lineDataSets: MutableList<ILineDataSet> = mutableListOf(lineDataSet1)
        val lineData = LineData(lineDataSets)
        mChart.data = lineData
        mChart.invalidate()
    }

}
