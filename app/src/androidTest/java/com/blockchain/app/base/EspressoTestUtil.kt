package com.blockchain.app.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.test.core.app.ActivityScenario
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher


/**
 * Utility methods for espresso tests.
 */
object EspressoTestUtil {
    /**
     * Disables progress bar animations for the views of the given activity rule
     *
     * @param activityTestRule The activity rule whose views will be checked
     */
    fun disableProgressBarAnimations(activityTestRule: ActivityScenario<out FragmentActivity>) {
        activityTestRule.onActivity { activity ->
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(
                            object : FragmentManager.FragmentLifecycleCallbacks() {
                                override fun onFragmentViewCreated(
                                        fm: FragmentManager,
                                        f: Fragment,
                                        v: View,
                                        savedInstanceState: Bundle?
                                ) {
                                    // traverse all views, if any is a progress bar, replace its animation
                                    traverseViews(v)
                                }
                            }, true
                    )
        }
    }

    private fun traverseViews(view: View?) {
        if (view is ViewGroup) {
            traverseViewGroup(view)
        } else if (view is ProgressBar) {
            disableProgressBarAnimation(view)
        }
    }

    private fun traverseViewGroup(view: ViewGroup) {
        val count = view.childCount
        (0 until count).forEach {
            traverseViews(view.getChildAt(it))
        }
    }

    /**
     * necessary to run tests on older API levels where progress bar uses handler loop to animate.
     *
     * @param progressBar The progress bar whose animation will be swapped with a drawable
     */
    private fun disableProgressBarAnimation(progressBar: ProgressBar) {
        progressBar.indeterminateDrawable = ColorDrawable(Color.BLUE)
    }

    fun checkMaxLength(length: Int): TypeSafeMatcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(item: View): Boolean {
                val filters = (item as TextView).filters
                val lengthFilter = filters[0] as InputFilter.LengthFilter

                return lengthFilter.max == length
            }

            override fun describeTo(description: Description) {
                description.appendText("checkMaxLength")
            }
        }
    }

    fun checkLengthEqualTo(length: Int): TypeSafeMatcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(item: View): Boolean {
                val count = (item as TextView).length()
                return count == length
            }

            override fun describeTo(description: Description) {
                description.appendText("checkMaxLength")
            }
        }
    }
}