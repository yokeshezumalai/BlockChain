package com.blockchain.app.features.chart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.blockchain.app.AppConfig
import com.blockchain.app.data.repository.ChartRepository
import com.blockchain.app.network.APIs
import com.blockchain.app.presentation.ChartViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest


class ChartViewModelTest {

    private val chartRepository = mock(ChartRepository::class.java)
    private val chartViewModel = ChartViewModel(chartRepository)
    private val api = mock(APIs::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Test
    fun testGetBitCoinChart() {

        val chartType = AppConfig.GET_MARKET_PRICE
        val timeSpan = AppConfig.ONE_YEAR

        testCoroutineDispatcher.runBlockingTest {
            // 1) Given coroutines have not started yet and the View Model is created
            testCoroutineDispatcher.pauseDispatcher()

            // Then the fast result has been emitted
            val fastResult = chartViewModel.getBitCoinChart(chartType, timeSpan).value
            assertTrue(fastResult?.data?.name.isNullOrEmpty())

            // 2) When the coroutine starts
            testCoroutineDispatcher.resumeDispatcher()

            // 3) Then the slow result has been emitted
            val slowResult = chartViewModel.getBitCoinChart(chartType, timeSpan).value
            slowResult?.data?.let {
                assertTrue(it.name!!.isNotEmpty())
                assertTrue(it.status!!.isNotEmpty())
                assertTrue(it.unit!!.isNotEmpty())
                assertTrue(it.period!!.isNotEmpty())
                assertTrue(it.description!!.isNotEmpty())
                assertTrue(it.values!!.isNotEmpty())
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetFilterMappedValues() {
        val chartType = AppConfig.GET_MARKET_PRICE
        val timeSpan = AppConfig.ONE_YEAR

        testCoroutineDispatcher.runBlockingTest {
            // 1) Given coroutines have not started yet and the View Model is created
            testCoroutineDispatcher.pauseDispatcher()

            // Then the fast result has been emitted
            val fastResult = chartViewModel.getBitCoinChart(chartType, timeSpan).value
            assertTrue(fastResult?.data?.name.isNullOrEmpty())

            // 2) When the coroutine starts
            testCoroutineDispatcher.resumeDispatcher()

            // 3) Then the slow result has been emitted
            val slowResult = chartViewModel.getBitCoinChart(chartType, timeSpan).value
            slowResult?.data?.let {
                val filterData = chartViewModel.getFilterMappedValues(it)
                assertTrue(filterData.name!!.isNotEmpty())
                assertTrue(filterData.description!!.isNotEmpty())
                assertTrue(filterData.bitcoinValues!!.isNotEmpty())
            }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetCurrentBitcoinValue() {
        val chartType = AppConfig.GET_MARKET_PRICE
        val timeSpan = AppConfig.ONE_YEAR

        testCoroutineDispatcher.runBlockingTest {
            // 1) Given coroutines have not started yet and the View Model is created
            testCoroutineDispatcher.pauseDispatcher()

            // Then the fast result has been emitted
            val fastResult = chartViewModel.getBitCoinChart(chartType, timeSpan).value
            assertTrue(fastResult?.data?.name.isNullOrEmpty())

            // 2) When the coroutine starts
            testCoroutineDispatcher.resumeDispatcher()

            // 3) Then the slow result has been emitted
            val slowResult = chartViewModel.getBitCoinChart(chartType, timeSpan).value
            slowResult?.data?.let {
                val currentBitCoinValue = chartViewModel.getCurrentBitcoinValue(it)
                currentBitCoinValue?.let {
                    assertTrue(it.isNotEmpty())
                }
            }
        }
    }


}