package com.blockchain.app.network

import com.blockchain.app.data.model.TransactionInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APIs {

    @GET("/charts/{chartType}")
    suspend fun getBitCoinChart(@Path("chartType") chartType: String, @Query("timespan") timespan: String?): TransactionInfo
}