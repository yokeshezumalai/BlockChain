package com.blockchain.app.data.repository

import com.blockchain.app.network.APIs
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ChartRepository @Inject constructor(private val apiHelper: APIs) {

    suspend fun getBitCoinChart(chartType: String, timeSpan: String?) = apiHelper.getBitCoinChart(chartType, timeSpan)
}
