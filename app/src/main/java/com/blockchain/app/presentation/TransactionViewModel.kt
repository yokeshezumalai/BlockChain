package com.blockchain.app.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.blockchain.app.data.repository.TransactionRepository
import javax.inject.Inject
import com.blockchain.app.data.model.SingleEntityData
import com.blockchain.app.data.utils.Resource
import com.blockchain.base.presentation.BaseViewModel
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.liveData
import com.blockchain.app.AppConfig
import com.blockchain.app.data.mapper.PriceMapper
import com.blockchain.app.data.model.MarketPriceDetails
import com.blockchain.app.data.model.TransactionInfo
import com.blockchain.app.widgets.BlockChainCustomChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class TransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository) :
    BaseViewModel() {


    val transactionInfo = MutableLiveData<TransactionInfo>()

    val timeSpanFilter = MutableLiveData<String>()

    /**
     * Method to get the bitcoin chart details
     */
    fun getBitCoinChart(chartType: String, timeSpan: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = transactionRepository.getBitCoinChart(chartType, timeSpan)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    /**
     * Method to filter the values
     */
    fun getFilterMappedValues(transactionInfoValue: TransactionInfo): MarketPriceDetails {
        return MarketPriceDetails(
            name = transactionInfoValue.name,
            description = transactionInfoValue.description,
            bitcoinValues = PriceMapper().mapToDomainModelList(transactionInfoValue.values).onEach {
                it.currency = Currency.getInstance(transactionInfoValue.unit)
            }
        )
    }

    /**
     * Method to get the current bitcoin value
     */
    fun getCurrentBitcoinValue(transactionInfo: TransactionInfo) : String{
        return getFilterMappedValues(transactionInfo).bitcoinValues.last().getPriceStringFormat()
    }

    /**
     * Method to set the current bitcoin value to the textview
     */
    fun setCurrentBitcoinValue(marketPrice: MarketPriceDetails) : String{
        return marketPrice.bitcoinValues.last().getPriceStringFormat()
    }
}
