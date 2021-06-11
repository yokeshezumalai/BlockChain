package com.blockchain.app.features

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.blockchain.app.AppConfig
import com.blockchain.app.R
import com.blockchain.app.data.model.TransactionInfo
import com.blockchain.app.data.utils.Resource
import com.blockchain.app.presentation.ChartActivity
import com.blockchain.app.presentation.ChartViewModel
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock


@RunWith(AndroidJUnit4::class)
class ChartFragmentTest() {


    private lateinit var viewModel: ChartViewModel

    private val results = MutableLiveData<Resource<TransactionInfo>>()

    private val chartType = AppConfig.GET_MARKET_PRICE
    private val timeSpan = AppConfig.ONE_YEAR

    @Rule
    @JvmField var activityRule: ActivityScenarioRule<ChartActivity> = ActivityScenarioRule(
        ChartActivity::class.java
    )

    @Before
    fun init() {
        viewModel = mock(ChartViewModel::class.java)
    }

    @Test
    fun testBasics_ProgressBar() {
        verifyProgressbarNotDisplayed()
    }

    @Test
    fun testSuccessResult_ChartVisible_BitcoinPriceVisible_ChartDescriptionVisible_ErrorNotVisible() {

        val bitcoinCurrentPrice = "30,235.89"
        val fastResult = viewModel.getBitCoinChart(chartType, timeSpan).value
        fastResult?.data?.let {

            results.postValue(Resource.success(it))

            // Chart Visible
            verifyBitcoinChartIsDisplayed()

            // Bitcoin current price displayed with Text
            verifyViewDisplayedWithText(R.id.currentBitcoinValue, bitcoinCurrentPrice)

            // Bitcoin chart description displayed with Text
            verifyViewDisplayedWithText(
                R.id.infoTitle,
                "Market Price"
            )
        }

    }

    @Test
    fun testSideDrawerOpen() {

        performClickOnView(R.id.startIcon)
        verifySideDrawerIsDisplayed()

    }

    @Test
    fun testSideMenuItemClick() {

        performClickOnView(R.id.startIcon)
        verifySideDrawerIsDisplayed()

        performClickOnView(R.id.marketPrice)
        verifySideDrawerClosed()
        testSuccessResult_ChartVisible_BitcoinPriceVisible_ChartDescriptionVisible_ErrorNotVisible()

    }


    private fun verifyProgressbarNotDisplayed() {
        Espresso.onView(withId(R.id.loadingBar))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
    }

    private fun verifyBitcoinChartIsDisplayed() {
        Espresso.onView(withId(R.id.valuesChart))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun verifyViewDisplayedWithText(viewId: Int, textToVerify: String?) {
        Espresso.onView(withId(viewId))
            .check(ViewAssertions.matches(ViewMatchers.withText(textToVerify)))
    }

    private fun performClickOnView(viewId: Int) {
        Espresso.onView(withId(viewId)).perform(ViewActions.click())
    }

    private fun verifySideDrawerIsDisplayed() {
        Espresso.onView(withId(R.id.navigationView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun verifySideDrawerClosed() {
        Espresso.onView(withId(R.id.navigationView))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
    }
}