package com.base.myapplication

import androidx.lifecycle.Observer
import com.base.core.datasource.model.ArticlesItem
import com.base.core.datasource.network.ConsumeResult
import com.base.myapplication.domain.MainActivityUseCase
import com.base.myapplication.features.fragA.ExampleFragmentViewModel
import com.base.myapplication.helper.BaseTestInstantTaskExecutorRule
import com.base.myapplication.helper.MockkConst.failedExpectedResult
import com.base.myapplication.helper.MockkConst.successExpectedResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Test


/**
 * Created by Wildan Nafian on 15/08/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
@ExperimentalCoroutinesApi
class ExampleFragmentViewModelTest : BaseTestInstantTaskExecutorRule() {

    private val fakeUsecase: MainActivityUseCase = mockk()
    private val observer: Observer<ConsumeResult<List<ArticlesItem>>> = mockk(relaxUnitFun = true)
    private val sut = ExampleFragmentViewModel(fakeUsecase)

    @Test
    fun successHandleSuccessResponse() {
        every {
            fakeUsecase.getSomething()
        } returns flowOf(ConsumeResult.onSuccess(successExpectedResult.articles))

        sut.getSomething().observeForever(observer)

        verifySequence {
            observer.onChanged(ConsumeResult.onLoading(true))
            observer.onChanged(ConsumeResult.onSuccess(successExpectedResult.articles))
            observer.onChanged(ConsumeResult.onLoading(false))
        }
    }

    @Test
    fun successHandleFailedResponse() {
        every {
            fakeUsecase.getSomething()
        } returns flowOf(ConsumeResult.onError(failedExpectedResult.message ?: ""))

        sut.getSomething().observeForever(observer)

        verifySequence {
            observer.onChanged(ConsumeResult.onLoading(true))
            observer.onChanged(ConsumeResult.onError(failedExpectedResult.message ?: ""))
            observer.onChanged(ConsumeResult.onLoading(false))
        }
    }
}