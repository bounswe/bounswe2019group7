package com.example.app.tradersapp.Fragments


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup

import com.example.app.tradersapp.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_currencies.*
import android.support.v4.content.ContextCompat
import android.graphics.drawable.Drawable
import com.github.mikephil.charting.utils.Utils.getSDKInt
import android.graphics.DashPathEffect
import com.github.mikephil.charting.utils.Utils


class CurrenciesFragment : Fragment() {

    private var baseCurrency = "TRY"    // TRY is the base currency by default
    private var targetCurrency = "EUR"  // EUR is the target currency by default
    private var prevBaseId = 0
    private var prevTargetId = 0

    private lateinit var mChart: LineChart


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currencies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prevBaseId = baseCurrencyRadioGroup.checkedRadioButtonId
        prevTargetId = targetCurrencyRadioGroup.checkedRadioButtonId

        createPlot()

        baseCurrencyRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedButton = group.findViewById<RadioButton>(checkedId)
            if(checkedButton.text != targetCurrency){
                updateButtonColors(prevBaseId, checkedButton,baseCurrencyRadioGroup)
                prevBaseId = checkedId
                baseCurrency = checkedButton.text.toString()
                //createPlot()
            }

        }

        targetCurrencyRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedButton = group.findViewById<RadioButton>(checkedId)
            if(checkedButton.text != baseCurrency){
                updateButtonColors(prevTargetId, checkedButton,targetCurrencyRadioGroup)
                prevTargetId = checkedId
                targetCurrency = checkedButton.text.toString()
                //createPlot()
            }
        }
    }

    private fun updateButtonColors(previousCheckedId: Int, newlyCheckedButton: RadioButton, radioGroup: RadioGroup) {
        newlyCheckedButton.background = (resources.getDrawable(R.drawable.round_button, null))
        radioGroup.findViewById<RadioButton>(previousCheckedId).background = null
    }

    private fun createPlot(){
        mChart = chart
        mChart.setTouchEnabled(true)
        mChart.setPinchZoom(true)

        mChart.legend.textColor = Color.WHITE
        mChart.description.isEnabled = false

        mChart.xAxis.textColor = Color.WHITE
        mChart.axisLeft.textColor = Color.WHITE
        mChart.axisRight.textColor = Color.WHITE

        val entries1 = mutableListOf(Entry(1f,2f), Entry(2f,2f),Entry(3f,5f),Entry(7f,2f))

        val lineDataSet1 = LineDataSet(entries1, "Currency")
        lineDataSet1.color = Color.RED
        lineDataSet1.setDrawValues(false)
        lineDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT)

        lineDataSet1.setDrawIcons(false)
        lineDataSet1.enableDashedLine(10f, 5f, 0f)
        lineDataSet1.enableDashedHighlightLine(10f, 5f, 0f)
        lineDataSet1.setColor(Color.WHITE)
        lineDataSet1.setCircleColor(Color.WHITE)
        lineDataSet1.setLineWidth(1f)
        lineDataSet1.setCircleRadius(3f)
        lineDataSet1.setDrawCircleHole(false)
        lineDataSet1.setValueTextSize(9f)
        lineDataSet1.setDrawFilled(true)
        lineDataSet1.setFormLineWidth(1f)
        lineDataSet1.setFormLineDashEffect(DashPathEffect(floatArrayOf(10f, 5f), 0f))
        lineDataSet1.setFormSize(15f)
        lineDataSet1.setFillColor(Color.WHITE)
        lineDataSet1.setValueTextColors(mutableListOf(Color.WHITE))


        val lineDataSets: MutableList<ILineDataSet> = mutableListOf(lineDataSet1)
        val lineData = LineData(lineDataSets)
        mChart.data = lineData
        mChart.invalidate()
    }

}
