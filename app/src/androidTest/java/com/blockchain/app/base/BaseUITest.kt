package com.blockchain.app.base

import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import util.SingleFragmentActivity
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseUITest {

    @Rule
    @JvmField
    val activityRule: ActivityScenario<SingleFragmentActivity> = ActivityScenario.launch(
        SingleFragmentActivity::class.java)

    @Rule
    @JvmField
    val executorRule = TaskExecutorWithIdlingResourceRule()

    abstract val testFragment: Fragment

    @Before
    open fun init() {
        activityRule.onActivity {
            it.setFragment(testFragment)
            EspressoTestUtil.disableProgressBarAnimations(activityRule)
        }
    }
}