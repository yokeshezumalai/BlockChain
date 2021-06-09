package com.blockchain.app.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.blockchain.app.R
import com.blockchain.app.data.model.MarketValue
import com.blockchain.app.data.utils.Resource.Companion.loading
import com.blockchain.app.helper.DateAxisValueFormatter
import com.blockchain.app.helper.PriceAxisValueFormatter
import com.blockchain.app.helper.PriceMarkerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BlockChainCustomChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LineChart(context, attrs, defStyle) {

    companion object {
        const val CHART_X_ANIMATION_DURATION = 2000
        val DEFAULT_ANIMATION_EASING: Easing.EasingFunction = Easing.EaseInOutQuad
    }

    private val xAxisValueFormatter = DateAxisValueFormatter()
    private val yAxisValueFormatter = PriceAxisValueFormatter()

    init {
        // No description or legend needed
        description.isEnabled = false
        legend.isEnabled = false

        // Disable any kind of zoom behaviour
        setScaleEnabled(false)
        isDoubleTapToZoomEnabled = false

        // Let user touch to highlight a selected value
        setTouchEnabled(true)

        setBorderColor(ContextCompat.getColor(context, R.color.main_app_color))
        marker = PriceMarkerView(context, R.layout.view_chart_marker).apply {
            chartView = this@BlockChainCustomChart
        }

        setupNoDataText()
        setupXAxis()
        setupYAxis()
    }

    fun show(bitcoinValues: List<MarketValue>) {
        // To show loading message
        data = null
        invalidate()

        CoroutineScope(Dispatchers.Default).launch {
            data = getData(bitcoinValues)

            withContext(Dispatchers.Main) {
                // To remove highlight value when new values are displayed
                highlightValue(null)
                animateX(CHART_X_ANIMATION_DURATION, DEFAULT_ANIMATION_EASING)
            }
        }
    }

    private fun setupXAxis() {
        xAxis.valueFormatter = xAxisValueFormatter
        xAxis.gridColor = ContextCompat.getColor(context, R.color.secondary_color)
        xAxis.textColor = ContextCompat.getColor(context, R.color.main_app_color)
        xAxis.setDrawAxisLine(false)
    }

    private fun setupYAxis() {
        axisLeft.valueFormatter = yAxisValueFormatter
        axisLeft.textColor = ContextCompat.getColor(context, R.color.main_app_color)
        axisLeft.setDrawAxisLine(false)
        axisLeft.setDrawZeroLine(false)
        axisLeft.setDrawGridLines(false)

        axisRight.isEnabled = false
    }

    private fun setupNoDataText(
        text: String = "Loading",
        @ColorInt textColor: Int =  ContextCompat.getColor(context, R.color.main_app_color)
    ) {
        setNoDataText(text)
        setNoDataTextColor(textColor)
    }

    private fun getData(bitcoinValues: List<MarketValue>): LineData {
        val entries = bitcoinValues.map {
            Entry(it.dateMillis.toFloat(), it.price.toFloat(), it.getPriceStringFormat())
        }
        val lineDataSet = getLineDataSet(entries)

        return LineData(lineDataSet)
    }

    private fun getLineDataSet(entries: List<Entry>) = LineDataSet(entries, "").apply {
        color = ContextCompat.getColor(context, R.color.main_app_color)
        fillDrawable =
            ContextCompat.getDrawable(context, R.drawable.gradient_primary_to_transparent)
        valueTextSize = 0f

        setDrawFilled(true)
        setDrawCircles(false)

        highLightColor = ContextCompat.getColor(context, R.color.secondary_color)
        setDrawHorizontalHighlightIndicator(false)
    }
}