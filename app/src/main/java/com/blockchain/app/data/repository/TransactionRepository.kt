package com.blockchain.app.data.repository

import com.blockchain.app.network.ApiClientInterface
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TransactionRepository @Inject constructor(private val apiHelper: ApiClientInterface) {
    /*fun getTransaction(): Observable<TransactionInfo> {
        return apiClientInterface.getBitCoinTransactions()
    }*/

    suspend fun getMarketPriceChart() = apiHelper.getMarketPriceChart()


/*    override fun getBitcoinInfo(): Flow<TransactionInfo> = flow {
        emit(apiClientInterface.getMarketPriceChart())
    }.flowOn(defaultDispatcher)*/
}
