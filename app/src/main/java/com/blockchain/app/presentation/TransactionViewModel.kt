package com.blockchain.app.presentation

import com.blockchain.app.data.repository.TransactionRepository
import javax.inject.Inject
import com.blockchain.app.data.model.SingleEntityData
import com.blockchain.app.data.utils.Resource
import com.blockchain.base.presentation.BaseViewModel
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.liveData


class TransactionViewModel @Inject constructor(private val transactionRepository: TransactionRepository) : BaseViewModel() {


    fun createDataSetForChart(entityData: List<SingleEntityData>) : ArrayList<Entry>{
        val lineEntries: ArrayList<Entry> = ArrayList()
        for(e in entityData){
            val entry = Entry(e.x.toFloat(),e.y.toFloat())
            lineEntries.add(entry)
        }
        return lineEntries
    }


    fun getMarketPriceChart() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = transactionRepository.getMarketPriceChart()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
