package com.blockchain.app.data.rest.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.blockchain.app.AppConfig
import com.blockchain.app.data.repository.ChartRepository
import com.blockchain.app.network.APIs
import kotlinx.coroutines.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import kotlin.coroutines.suspendCoroutine
import com.blockchain.app.util.ApiUtil.verifyBlocking
import junit.framework.Assert
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest

@RunWith(JUnit4::class)
class ChartRepositoryTest {
    private val api = mock(APIs::class.java)
    private val repo = ChartRepository(api)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    @ExperimentalCoroutinesApi
    fun testGetBitCoinChart() {

        val chartType = AppConfig.GET_MARKET_PRICE
        val timeSpan = AppConfig.ONE_YEAR

        GlobalScope.launch(Dispatchers.IO) {
            suspendCoroutine<Unit> {
                runBlockingTest {
                    launch {
                        repo.getBitCoinChart(chartType, timeSpan)
                    }
                    verifyBlocking(api) { getBitCoinChart(chartType, timeSpan) }
                }
            }
        }

    }
}