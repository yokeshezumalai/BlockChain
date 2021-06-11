package com.blockchain.app.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blockchain.app.AppConfig
import javax.inject.Inject
import com.blockchain.app.data.model.TransactionInfo
import com.blockchain.app.R
import com.blockchain.app.data.utils.Status
import com.blockchain.app.databinding.ChartFragmentBinding
import com.blockchain.app.di.Injectable
import com.blockchain.app.widgets.BCTitleBar
import com.blockchain.base.presentation.BaseFragment
import com.blockchain.base.presentation.BaseViewModelFactory
import kotlinx.android.synthetic.main.chart_fragment.*
import kotlinx.android.synthetic.main.main_side_menu.*
import util.OpenForTesting

@OpenForTesting
open class ChartFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory

    private lateinit var viewModel: ChartViewModel

    private lateinit var binding: ChartFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        DataBindingUtil.inflate<ChartFragmentBinding>(inflater, R.layout.chart_fragment, container, false).also {
            binding = it
            viewModel = ViewModelProvider(this, viewModelFactory).get(ChartViewModel::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    /**
     * Method to setup view
     */
    private fun setupView(){

        /**
         * Custom Title Bar
         */
        titleBar.setListener(object : BCTitleBar.Callback {
            override fun onStartButtonClick() {
                drawer.openDrawer(GravityCompat.START)
            }

            override fun onExtraButtonClick() {
                FilterDialogFragment.show(
                    requireActivity(),
                    callback = object : FilterDialogFragment.Callback {
                        override fun onDialogClose(filter: String?) {
                            if(!filter.isNullOrEmpty()){
                                viewModel.timeSpan.value = filter
                                fetchTransactionData()
                            }
                        }
                    })
            }
        })

        viewModel.chartName.value = context?.getString(R.string.market_price_usd)

        fetchTransactionData()

        // Side Menu - To display the different data with Different charts
        marketPrice.setOnClickListener {
            drawer.closeDrawer(GravityCompat.START)
            viewModel.chartName.value = marketPrice.text.toString()
            viewModel.chartType.value = AppConfig.GET_MARKET_PRICE
            fetchTransactionData()
        }

        totalBitCoins.setOnClickListener {
            drawer.closeDrawer(GravityCompat.START)
            viewModel.chartName.value = totalBitCoins.text.toString()
            viewModel.chartType.value = AppConfig.TOTAL_BITCOINS
            fetchTransactionData()
        }

        transactionFees.setOnClickListener {
            drawer.closeDrawer(GravityCompat.START)
            viewModel.chartName.value = transactionFees.text.toString()
            viewModel.chartType.value = AppConfig.TRANSACTION_FEES
            fetchTransactionData()
        }

        transactionsPerSecond.setOnClickListener {
            drawer.closeDrawer(GravityCompat.START)
            viewModel.chartName.value = transactionsPerSecond.text.toString()
            viewModel.chartType.value = AppConfig.TRANSACTIONS_PER_SECOND
            fetchTransactionData()
        }

        marketCap.setOnClickListener {
            drawer.closeDrawer(GravityCompat.START)
            viewModel.chartName.value = marketCap.text.toString()
            viewModel.chartType.value = AppConfig.MARKET_CAP
            fetchTransactionData()
        }

        exchangeTradeVolume.setOnClickListener {
            drawer.closeDrawer(GravityCompat.START)
            viewModel.chartName.value = exchangeTradeVolume.text.toString()
            viewModel.chartType.value = AppConfig.TRADE_VOLUME
            fetchTransactionData()
        }

        avgBlockSize.setOnClickListener {
            drawer.closeDrawer(GravityCompat.START)
            viewModel.chartName.value = avgBlockSize.text.toString()
            viewModel.chartType.value = AppConfig.AVG_BLOCK_SIZE
            fetchTransactionData()
        }
    }

    /**
     * Method to fetch the transactions
     */
    private fun fetchTransactionData() {
        viewModel.getBitCoinChart().observe(viewLifecycleOwner, Observer {
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
                        Toast.makeText(context, context?.getString(R.string.transaction_data_error), Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        loadingBar.visibility = View.VISIBLE
                        valuesChart.visibility = View.GONE
                    }
                }
            }
        })
    }

    /**
     * Method to set data
     */
    private fun setData(data: TransactionInfo){
        viewModel.transactionInfo.value = data
        val chartData = viewModel.getFilterMappedValues(data)
        chartData.bitcoinValues?.let { valuesChart?.show(it) }
        viewModel.setCurrentBitcoinValue(chartData)
    }


}