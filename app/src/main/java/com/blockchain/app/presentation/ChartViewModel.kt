package com.blockchain.app.presentation

import androidx.lifecycle.MutableLiveData
import com.blockchain.app.data.repository.ChartRepository
import javax.inject.Inject
import com.blockchain.app.data.utils.Resource
import com.blockchain.base.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.liveData
import com.blockchain.app.data.mapper.PriceMapper
import com.blockchain.app.data.model.MarketPriceDetails
import com.blockchain.app.data.model.TransactionInfo
import java.util.*


class ChartViewModel @Inject constructor(private val transactionRepository: ChartRepository) :
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
            bitcoinValues = PriceMapper().mapToDomainModelList(transactionInfoValue.values)?.onEach {
                it.currency = Currency.getInstance(transactionInfoValue.unit)
            }
        )
    }

    /**
     * Method to get the current bitcoin value
     */
    fun getCurrentBitcoinValue(transactionInfo: TransactionInfo) : String?{
        return getFilterMappedValues(transactionInfo).bitcoinValues?.last()?.getPriceStringFormat()
    }

    /**
     * Method to set the current bitcoin value to the textview
     */
    fun setCurrentBitcoinValue(marketPrice: MarketPriceDetails) : String?{
        return marketPrice.bitcoinValues?.last()?.getPriceStringFormat()
    }
}
