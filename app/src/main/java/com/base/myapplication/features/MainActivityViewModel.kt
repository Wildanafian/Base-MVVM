package com.base.myapplication.features

import androidx.lifecycle.*
import com.base.myapplication.data.model.ArticlesItem
import com.base.myapplication.data.model.ArticlesItemFiltered
import com.base.myapplication.data.repository.local.sharedpreference.SharedPref
import com.base.myapplication.data.repository.remote.network.ConsumeResult
import com.base.myapplication.domain.MainActivityUseCase
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

class MainActivityViewModel @Inject constructor(
    private val useCase: MainActivityUseCase,
    private val preferences: SharedPref
) :
    ViewModel() {

    //sample 1
    fun getSomething(): LiveData<ConsumeResult<List<ArticlesItem>>> {
        return liveData {
            useCase.getSomething()
                .onStart { emit(ConsumeResult.onLoading(true)) }
                .onCompletion {
                    emit(ConsumeResult.onLoading(false))
                    preferences.writeTo("testpref", "cakeeep2")
                }
                .collect { emit(it) }
        }
    }

    //sample 2
//    fun getSomething(): LiveData<ConsumeResult<List<ArticlesItem>>> {
//        return useCase.getSomething().asLiveData()
//    }

    //sample rxjava
    private var disposable: CompositeDisposable = CompositeDisposable()

    private val _newsData = MutableLiveData<ConsumeResult<List<ArticlesItemFiltered>>>()
    val newsData: LiveData<ConsumeResult<List<ArticlesItemFiltered>>> = _newsData

    init {
        viewModelScope.launch(Dispatchers.Main) {
            disposable.add(
                useCase.getSomethingRxJava()
                    .doOnSubscribe {
                        _newsData.value = ConsumeResult.onLoading(true)
                    }
                    .doAfterTerminate {
                        _newsData.value = ConsumeResult.onLoading(false)
                    }
                    .subscribe({
                        _newsData.value = ConsumeResult.onSuccess(it)
                    }, {
                        _newsData.value = ConsumeResult.onError(Exception(it))
                    })
            )
        }
    }

    fun getPref(): String {
        return preferences.readString("testpref")
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}