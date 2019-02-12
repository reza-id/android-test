package com.reza.testingexample.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.reza.testingexample.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testAppBehaviour() {


        // make sure that a spinner is displayed
        onView(withId(R.id.region_spinner)).check(matches(isDisplayed()))

        // perform click to the spinner
        onView(withId(R.id.region_spinner)).perform(click())
        // select one of the spinner item
        onView(withText("Asia")).perform(click())

        Thread.sleep(3000)

        // the result is: some countries are displayed
        // and contains "Indonesia" as part of selected region
        onView(withId(R.id.list_countries)).check(matches(isDisplayed()))
//        onView(withId(R.id.list_countries)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(12))

//        Thread.sleep(1000)
        onView(withText("Afghanistan")).check(matches(isDisplayed()))
        onView(withText("Afghanistan")).perform(click())
//
//        onView(withId(add_to_favorite))
//            .check(matches(isDisplayed()))
//        onView(withId(add_to_favorite)).perform(click())
//        onView(withText("Added to favorite"))
//            .check(matches(isDisplayed()))
//        pressBack()
//
//        onView(withId(bottom_navigation))
//            .check(matches(isDisplayed()))
//        onView(withId(favorites)).perform(click())
    }

}