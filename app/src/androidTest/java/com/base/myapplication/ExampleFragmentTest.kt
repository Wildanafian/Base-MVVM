package com.base.myapplication

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.base.myapplication.base.BaseTestWithIdlingResource
import com.base.myapplication.features.MainActivity
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Wildan Nafian on 13/08/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ExampleFragmentTest : BaseTestWithIdlingResource() {

    @Test
    fun sample_test() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.tvHello))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnMoveToB))
            .check(matches(isDisplayed()))

        scenario.close()
    }
}