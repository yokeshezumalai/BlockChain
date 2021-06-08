package com.blockchain.app.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import javax.inject.Inject
import com.blockchain.app.data.model.TransactionInfo
import com.blockchain.app.R
import com.blockchain.app.data.utils.Status
import com.blockchain.app.di.Injectable
import com.blockchain.base.presentation.BaseFragment
import com.blockchain.base.presentation.BaseViewModelFactory
import kotlinx.android.synthetic.main.layout_transaction.*


class TransactionFragment : BaseFragment(), Injectable {
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

    private fun fetchTransactionData() {
        viewModel.getBitCoinChart(false).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        loadingBar.visibility = View.GONE
                        valuesChart.visibility = View.VISIBLE
                        it.data?.let {
                            viewModel.transactionInfo.value = it as TransactionInfo
                            val chartData = viewModel.getFilterMappedValues(it)
                            valuesChart?.show(chartData.bitcoinValues)
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
        
        viewModel.transactionInfo.observe(viewLifecycleOwner, Observer {
            currentBitcoinValue.text = viewModel.getCurrentBitcoinValue(it)
        })
    }


}