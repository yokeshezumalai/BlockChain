package com.blockchain.app.data.rest.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.blockchain.app.AppConfig
import com.blockchain.app.network.APIs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class APIsTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: APIs

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIs::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun testGetMovies() {
        enqueueResponse("get_chart_data.json")
        runBlocking {

            launch {
                val responseBody = (service.getBitCoinChart(AppConfig.GET_MARKET_PRICE, AppConfig.ONE_YEAR))

                responseBody.let {
                    assertThat(it, notNullValue())
                    assertThat(it.name, notNullValue())
                    assertThat(it.description, notNullValue())
                    assertThat(it.period, notNullValue())
                    assertThat(it.status, notNullValue())
                    assertThat(it.unit, notNullValue())
                    assertThat(it.values, notNullValue())

                    it.values?.forEach {
                        assertThat(it.x, notNullValue())
                        assertThat(it.y, notNullValue())
                    }
                }
            }
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
            .source()
            .buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(
            mockResponse
                .setBody(inputStream.readString(Charsets.UTF_8))
        )
    }

}