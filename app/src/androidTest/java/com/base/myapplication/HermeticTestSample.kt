package com.base.myapplication

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.base.myapplication.base.BaseTestWithMockWebserver
import com.base.myapplication.features.MainActivity
import com.base.myapplication.helper.MockkWebServerConst.failedData
import com.base.myapplication.helper.MockkWebServerConst.mockWebServer
import com.base.myapplication.helper.MockkWebServerConst.succesData
import com.base.myapplication.helper.enqueueFakeResponse
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Wildan Nafian on 11/08/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class HermeticTestSample : BaseTestWithMockWebserver() {

    @Test
    fun launch_activity() {
        mockWebServer.dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/everything?q=tesla&page=1&apiKey=a5bf56b6153c4ee9bd64368cba3e1317" ->
                        enqueueFakeResponse(succesData, 200)
                    else ->
                        enqueueFakeResponse(failedData, 404)
                }
            }
        }

//        launchFragmentInHiltContainer<ExampleFragment>(Bundle(), R.style.ThemeMyApplication)
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        val request1: RecordedRequest = mockWebServer.takeRequest()
        assertEquals(request1.path, "/everything?q=tesla&page=1&apiKey=a5bf56b6153c4ee9bd64368cba3e1317")

        onView(withId(R.id.tvHello))
            .check(matches(withText("Tesla pobita. Teraz to Taycan jest najszybszy [WIDEO]")))

        onView(withId(R.id.btnMoveToB))
            .perform(click())

        onView(withId(R.id.textView))
            .check(matches(withText("Screen fragment B")))

        scenario.close()
    }
}