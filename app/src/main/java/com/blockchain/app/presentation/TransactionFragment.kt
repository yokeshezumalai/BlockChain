package com.blockchain.app.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject
import com.blockchain.app.data.model.SingleEntityData
import com.blockchain.app.data.model.TransactionInfo
import dagger.android.support.AndroidSupportInjection
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.components.XAxis
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import com.blockchain.app.R
import com.blockchain.app.data.utils.Status
import com.blockchain.base.presentation.BaseFragment
import com.blockchain.base.presentation.BaseViewModelFactory
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.layout_progress.*
import kotlinx.android.synthetic.main.layout_transaction.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TransactionFragment : BaseFragment() {
    private val TAG = "TransactionFragment"
    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory
    private val viewModel: TransactionViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(TransactionViewModel::class.java)
    }

    override fun layoutRes(): Int {
        return R.layout.layout_transaction
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fetchTransactionData()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun fetchTransactionData() {
        viewModel.getMarketPriceChart().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        chartView.visibility = View.VISIBLE
                        this.updateTransaction(it.data)
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        chartView.visibility = View.GONE
                        Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        chartView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun updateTransaction(transactionInfo: TransactionInfo?) {
        setupUI(transactionInfo)
        createChart(transactionInfo?.values)
    }

    private fun setupUI(transactionInfo: TransactionInfo?) {
        chartHeader.text = transactionInfo?.name
        chartDescription.text = transactionInfo?.description
        chart_info.text = resources.getString(R.string.chart_info_text)
    }

    private fun createChart(entries: List<SingleEntityData>?) {
        Log.d(TAG, "Entries = ${entries.toString()}")
        if (entries != null) {
            val lineEntries: ArrayList<Entry> = viewModel.createDataSetForChart(entries)
            val dataSet = LineDataSet(lineEntries, context?.getString(R.string.market_price_usd))
            val data = LineData(dataSet)
            val dateArray : ArrayList<String> = getDatedArray(entries)
            chartView.data = data
            formatChart(chartView, dataSet,dateArray)
            chartView.notifyDataSetChanged()
            chartView.invalidate()
        } else {
            Log.e(TAG, resources.getString(R.string.transaction_data_error))
        }
    }

    private fun getDatedArray(entries: List<SingleEntityData>) : ArrayList<String> {
        val dateArrayList : ArrayList<String> = ArrayList()
        for(e in entries){
            dateArrayList.add(convertLongtoDate(e.x))
        }
        return dateArrayList
    }

    private fun convertLongtoDate(l:Long) : String {
        try {
            val sdf = SimpleDateFormat("yyyy-MMM-dd", Locale.getDefault())
            return sdf.format(Date(l)).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun formatChart(chart: LineChart, dataSet: LineDataSet, dateArray:ArrayList<String>) {
        val backgroundColor = ContextCompat.getColor(context!!, R.color.colorBlue)
        val xAxis = chart.xAxis
        xAxis.textColor = ContextCompat.getColor(context!!, R.color.white)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1F
        xAxis.textSize = 8F
        xAxis.labelRotationAngle = 30F

        xAxis.axisMinimum = dataSet.xMin
        xAxis.axisMaximum = dataSet.xMax

        xAxis.axisLineColor = backgroundColor
        xAxis.axisLineWidth = 0.5f

        //TODO valueformatter doesn't take long as input, which doesn't give correct date (fix require).
        xAxis.valueFormatter = IndexAxisValueFormatter(dateArray)
        xAxis.setAvoidFirstLastClipping(true)

        val yAxisLeft = chart.axisLeft
        yAxisLeft.axisLineColor = backgroundColor
        yAxisLeft.textColor = ContextCompat.getColor(context!!, R.color.white)
        yAxisLeft.axisLineWidth = 0.5f

        val yAxisRight = chart.axisRight
        yAxisRight.isEnabled = false

        chart.setDrawGridBackground(false)
        chart.setBackgroundColor(backgroundColor)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(false)
        chart.description.isEnabled = false

        val legend = chart.legend
        legend.isEnabled = true

        setupDataSet(dataSet)
    }

    private fun setupDataSet(dataSet: LineDataSet) {
        dataSet.setDrawCircles(false)
        dataSet.setDrawFilled(false)
        dataSet.setDrawValues(false)
        dataSet.setColors(Color.WHITE)
        dataSet.lineWidth = 2f
    }


}