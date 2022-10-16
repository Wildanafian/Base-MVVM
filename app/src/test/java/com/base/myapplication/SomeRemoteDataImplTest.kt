package com.base.myapplication

import com.base.core.datasource.model.ResponseDataError
import com.base.core.datasource.network.ApiInterface
import com.base.core.datasource.network.RemoteResult
import com.base.myapplication.data.datasource.remote.SomeRemoteDataImpl
import com.base.myapplication.helper.MockkConst.failedData
import com.base.myapplication.helper.MockkConst.failedExpectedResult
import com.base.myapplication.helper.MockkConst.successExpectedResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response
import java.net.SocketTimeoutException

/**
 * Created by Wildan Nafian on 13/08/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
class SomeRemoteDataImplTest {

    private val fakeApi: ApiInterface = mockk()
    private val fakeDispatcher = Dispatchers.IO
    private val sut = SomeRemoteDataImpl(fakeApi, fakeDispatcher)

    @Test
    fun successHandleSuccessResponseFromRemoteSource() = runBlocking {
        coEvery {
            fakeApi.getAllNews()
        } returns Response.success(200, successExpectedResult)

        assertEquals(RemoteResult.onSuccess(successExpectedResult), sut.getSomeData())
    }

    @Test
    fun successHandleFailedResponseFromRemoteSource() = runBlocking {
        coEvery {
            fakeApi.getAllNews()
        } returns Response.error(404, failedData.toResponseBody())

        assertEquals(RemoteResult.onError(404, failedExpectedResult), sut.getSomeData())
    }

    @Test
    fun successHandleExceptionFromRemoteSource() = runBlocking {
        coEvery {
            fakeApi.getAllNews()
        } throws SocketTimeoutException("you got SocketTimeoutException")

        assertEquals(
            RemoteResult.onError(0, ResponseDataError(message = "you got SocketTimeoutException")),
            sut.getSomeData()
        )
    }
}