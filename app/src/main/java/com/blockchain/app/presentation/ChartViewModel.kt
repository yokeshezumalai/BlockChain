package com.blockchain.app.presentation

import androidx.lifecycle.MutableLiveData
import com.blockchain.app.data.repository.ChartRepository
import javax.inject.Inject
import com.blockchain.app.data.utils.Resource
import com.blockchain.base.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.liveData
import com.blockchain.app.AppConfig
import com.blockchain.app.data.mapper.PriceMapper
import com.blockchain.app.data.model.MarketPriceDetails
import com.blockchain.app.data.model.TransactionInfo
import util.OpenForTesting
import java.util.*

@OpenForTesting
open class ChartViewModel @Inject constructor(private val transactionRepository: ChartRepository) :
    BaseViewModel() {


    val transactionInfo = MutableLiveData<TransactionInfo>()

    val timeSpanFilter = MutableLiveData<String>()

    val chartName = MutableLiveData<String>()
    var chartValue = MutableLiveData<String>()

    var chartType = MutableLiveData<String>(AppConfig.GET_MARKET_PRICE)
    var timeSpan = MutableLiveData<String>(AppConfig.THREE_MONTH)

    /**
     * Method to get the bitcoin chart details
     */
    fun getBitCoinChart() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = transactionRepository.getBitCoinChart(chartType.value, timeSpan.value)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    /**
     * Method to filter the values
     */
    fun getFilterMappedValues(transactionInfoValue: TransactionInfo): MarketPriceDetails {
        return if(transactionInfoValue.unit?.contains("USD")!!){
            MarketPriceDetails(
                name = transactionInfoValue.name,
                description = transactionInfoValue.description,
                unit = transactionInfoValue.unit,
                bitcoinValues = PriceMapper().mapToDomainModelList(transactionInfoValue.values)?.onEach {
                    it.currency = Currency.getInstance(transactionInfoValue.unit)
                }
            )
        }else{
            MarketPriceDetails(
                name = transactionInfoValue.name,
                description = transactionInfoValue.description,
                unit = transactionInfoValue.unit,
                bitcoinValues = PriceMapper().mapToDomainModelList(transactionInfoValue.values)
            )
        }
    }

    /**
     * Method to get the current bitcoin value
     */
    fun getCurrentBitcoinValue(transactionInfo: TransactionInfo): String?{
        return getFilterMappedValues(transactionInfo).bitcoinValues?.last()?.getPriceStringFormat()
    }

    /**
     * Method to set the current bitcoin value to the textview
     */
    fun setCurrentBitcoinValue(marketPrice: MarketPriceDetails){
        val chartText = marketPrice.bitcoinValues?.last()
        if(marketPrice.unit?.contains("USD")!!){
            chartValue.value = chartText?.getPriceStringFormat()
        }else{
            chartValue.value = chartText?.price?.toString()
        }
    }
}
