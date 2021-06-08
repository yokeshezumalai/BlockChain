package com.blockchain.app.network

import com.blockchain.app.data.model.TransactionInfo
import com.blockchain.app.utils.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiClientInterface {
    @GET(Constants.CHARTS_URL)
    fun getBitCoinTransactions(): Observable<TransactionInfo>

    @GET(Constants.CHARTS_URL)
    suspend fun getMarketPriceChart(): TransactionInfo
}