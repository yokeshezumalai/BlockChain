package com.blockchain.app.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blockchain.app.AppConfig
import javax.inject.Inject
import com.blockchain.app.data.model.TransactionInfo
import com.blockchain.app.R
import com.blockchain.app.data.utils.Status
import com.blockchain.app.di.Injectable
import com.blockchain.app.widgets.BCTitleBar
import com.blockchain.base.presentation.BaseFragment
import com.blockchain.base.presentation.BaseViewModelFactory
import kotlinx.android.synthetic.main.layout_transaction.*
import kotlinx.android.synthetic.main.main_side_menu.*


class TransactionFragment : BaseFragment(), Injectable {
    private val TAG = "TransactionFragment"
    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory

    private val viewModel: TransactionViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(TransactionViewModel::class.java)
    }

    companion object {
        private var chartType = AppConfig.GET_MARKET_PRICE
        private var timeSpan: String? = AppConfig.THREE_MONTH
    }

    override fun layoutRes(): Int {
        return R.layout.layout_transaction
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fetchTransactionData(chartType, timeSpan)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView(){

        titleBar.setListener(object : BCTitleBar.Callback {
            override fun onStartButtonClick() {
                drawer.openDrawer(GravityCompat.START)
            }

            override fun onExtraButtonClick() {
                FilterDialogFragment.show(
                    requireActivity(),
                    callback = object : FilterDialogFragment.Callback {
                        override fun onDialogClose(filter: String?) {
                            timeSpan = filter
                            fetchTransactionData(chartType, filter)
                        }
                    })
            }
        })

        marketPrice.setOnClickListener {
            infoTitle.text = marketPrice.text
            drawer.closeDrawer(GravityCompat.START)
            chartType = AppConfig.GET_MARKET_PRICE
            fetchTransactionData(chartType, timeSpan)
        }

        totalBitCoins.setOnClickListener {
            infoTitle.text = totalBitCoins.text
            drawer.closeDrawer(GravityCompat.START)
            chartType = AppConfig.TOTAL_BITCOINS
            fetchTransactionData(chartType, timeSpan)
        }

        transactionFees.setOnClickListener {
            infoTitle.text = transactionFees.text
            drawer.closeDrawer(GravityCompat.START)
            chartType = AppConfig.TRANSACTION_FEES
            fetchTransactionData(chartType, timeSpan)
        }

        transactionsPerSecond.setOnClickListener {
            infoTitle.text = transactionsPerSecond.text
            drawer.closeDrawer(GravityCompat.START)
            chartType = AppConfig.TRANSACTIONS_PER_SECOND
            fetchTransactionData(chartType, timeSpan)
        }

        marketCap.setOnClickListener {
            infoTitle.text = marketCap.text
            drawer.closeDrawer(GravityCompat.START)
            chartType = AppConfig.MARKET_CAP
            fetchTransactionData(chartType, timeSpan)
        }

        exchangeTradeVolume.setOnClickListener {
            infoTitle.text = exchangeTradeVolume.text
            drawer.closeDrawer(GravityCompat.START)
            chartType = AppConfig.TRADE_VOLUME
            fetchTransactionData(chartType, timeSpan)
        }

        avgBlockSize.setOnClickListener {
            infoTitle.text = avgBlockSize.text
            drawer.closeDrawer(GravityCompat.START)
            chartType = AppConfig.AVG_BLOCK_SIZE
            fetchTransactionData(chartType, timeSpan)
        }

        viewModel.transactionInfo.observe(viewLifecycleOwner, Observer {
            currentBitcoinValue.text = viewModel.getCurrentBitcoinValue(it)
        })
    }

    private fun fetchTransactionData(chartType: String, timeSpan: String?) {
        viewModel.getBitCoinChart(chartType, timeSpan).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loadingBar.visibility = View.GONE
                        valuesChart.visibility = View.VISIBLE
                        it.data?.let {
                            setData(it)
                        }
                    }
                    Status.ERROR -> {
                        loadingBar.visibility = View.GONE
                        valuesChart.visibility = View.GONE
                        Toast.makeText(context, "Error Occured", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        loadingBar.visibility = View.VISIBLE
                        valuesChart.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun setData(data: TransactionInfo){
        viewModel.transactionInfo.value = data
        val chartData = viewModel.getFilterMappedValues(data)
        currentBitcoinValue.text = viewModel.setCurrentBitcoinValue(chartData)
        valuesChart?.show(chartData.bitcoinValues)
    }


}