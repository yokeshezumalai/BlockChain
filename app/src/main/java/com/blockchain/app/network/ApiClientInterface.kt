package com.blockchain.app.network

import com.blockchain.app.data.model.TransactionInfo
import com.blockchain.app.utils.Constants
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiClientInterface {

    @GET(Constants.CHARTS_URL)
    suspend fun getMarketPriceChart(): TransactionInfo

    @GET(Constants.TRANSACATIONS_URL)
    fun getBitCoinTransactions(): Observable<TransactionInfo>
}