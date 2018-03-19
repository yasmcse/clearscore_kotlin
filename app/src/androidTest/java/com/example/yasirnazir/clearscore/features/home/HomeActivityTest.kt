package com.example.yasirnazir.clearscore.features.home


import android.content.Intent
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.test.suitebuilder.annotation.LargeTest

import com.example.yasirnazir.clearscore.R

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    var mActivityTestRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun homeActivityTest() {
        mActivityTestRule.launchActivity(Intent())
    }

    @Test
    @Throws(InterruptedException::class)
    fun displayProgressBar() {
        onView(withId(R.id.scoreProgressBar)).check(matches(isDisplayed()))
    }

    @Test
    @Throws(InterruptedException::class)
    fun displayScore() {
        onView(withId(R.id.score)).check(matches(isDisplayed()))
    }


}
