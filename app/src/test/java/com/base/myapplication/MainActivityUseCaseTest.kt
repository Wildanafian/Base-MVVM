package com.base.myapplication

import com.base.core.network.ConsumeResult
import com.base.myapplication.data.repository.SomeRepository
import com.base.myapplication.domain.MainActivityUseCaseImpl
import com.base.myapplication.helper.MockkConst.failedExpectedResult
import com.base.myapplication.helper.MockkConst.successExpectedResult
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * Created by Wildan Nafian on 15/08/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
class MainActivityUseCaseTest {

    private val fakeRepository: SomeRepository = mockk()
    private val sut = MainActivityUseCaseImpl(fakeRepository)

    @Test
    fun successHandleSuccessResponse() = runBlocking {
        every {
            fakeRepository.getSomething()
        } returns flowOf(ConsumeResult.onSuccess(successExpectedResult.articles))

        assertEquals(
            ConsumeResult.onSuccess(successExpectedResult.articles),
            sut.getSomething().first()
        )
    }

    @Test
    fun successHandleFailedResponse() = runBlocking {
        every {
            fakeRepository.getSomething()
        } returns flowOf(ConsumeResult.onError(failedExpectedResult.message ?: ""))

        assertEquals(
            ConsumeResult.onError(failedExpectedResult.message ?: ""),
            sut.getSomething().first()
        )
    }
}