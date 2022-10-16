package com.base.myapplication.features.fragA

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.base.core.datasource.model.ArticlesItem
import com.base.core.datasource.network.ConsumeResult
import com.base.myapplication.domain.MainActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */


@HiltViewModel
class ExampleFragmentViewModel @Inject constructor(
    private val useCase: MainActivityUseCase
) :
    ViewModel() {

    //sample 1
    fun getSomething(): LiveData<ConsumeResult<List<ArticlesItem>>> {
        return liveData {
            useCase.getSomething()
                .onStart { emit(ConsumeResult.onLoading(true)) }
                .onCompletion {
                    emit(ConsumeResult.onLoading(false))
                }
                .collect { emit(it) }
        }
    }

    //sample 2
//    fun getSomething(): LiveData<ConsumeResult<List<ArticlesItem>>> {
//        return useCase.getSomething().asLiveData()
//    }

}