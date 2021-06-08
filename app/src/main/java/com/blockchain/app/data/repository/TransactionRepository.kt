package com.blockchain.app.data.repository

import com.blockchain.app.network.ApiClientInterface
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TransactionRepository @Inject constructor(private val apiHelper: ApiClientInterface) {

    suspend fun getMarketPriceChart() = apiHelper.getMarketPriceChart()

    suspend fun getBitCoinTransactions() = apiHelper.getBitCoinTransactions()
}
