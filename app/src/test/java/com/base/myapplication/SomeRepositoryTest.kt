package com.base.myapplication

import com.base.core.network.ConsumeResult
import com.base.core.network.RemoteResult
import com.base.myapplication.data.repository.SomeRepositoryImpl
import com.base.myapplication.data.repository.remote.SomeRemoteData
import com.base.myapplication.helper.MockkConst.failedExpectedResult
import com.base.myapplication.helper.MockkConst.successExpectedResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.SocketTimeoutException


/**
 * Created by Wildan Nafian on 15/08/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
class SomeRepositoryTest {

    private val fakeRemoteSource: SomeRemoteData = mockk()
    private val fakeDispatchers = Dispatchers.IO
    private val sut = SomeRepositoryImpl(fakeRemoteSource, fakeDispatchers)

    @Test
    fun successHandleSuccessResponse() = runBlocking {
        coEvery {
            fakeRemoteSource.getSomeData()
        } returns RemoteResult.onSuccess(successExpectedResult)

        assertEquals(
            ConsumeResult.onSuccess(successExpectedResult.articles),
            sut.getSomething().first()
        )
    }

    @Test
    fun successHandleFailedResponse() = runBlocking {
        coEvery {
            fakeRemoteSource.getSomeData()
        } returns RemoteResult.onError(404, failedExpectedResult)

        assertEquals(
            ConsumeResult.onError(failedExpectedResult.message ?: ""),
            sut.getSomething().first()
        )
    }

    @Test
    fun successHandleExceptionResponse() = runBlocking {
        coEvery {
            fakeRemoteSource.getSomeData()
        } throws SocketTimeoutException()

        assertEquals(
            ConsumeResult.onError(SocketTimeoutException().localizedMessage ?: ""),
            sut.getSomething().first()
        )
    }
}