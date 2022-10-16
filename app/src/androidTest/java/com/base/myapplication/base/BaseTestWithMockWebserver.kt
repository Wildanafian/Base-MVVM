package com.base.myapplication.base

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.base.core.util.EspressoIdlingResource
import com.base.myapplication.helper.MockkWebServerConst
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith


/**
 * Created by Wildan Nafian on 12/08/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
open class BaseTestWithMockWebserver {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    open fun init() {
        hiltRule.inject()
        MockkWebServerConst.mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingresource)
    }

    @After
    open fun destroy() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingresource)
        MockkWebServerConst.mockWebServer.shutdown()
    }
}