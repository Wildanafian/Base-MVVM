package com.base.myapplication.data.repository.remote

import com.base.core.di.IoDispatcher
import com.base.core.model.ResponseNewsApi
import com.base.core.network.ApiInterface
import com.base.core.network.BaseDataSource
import com.base.core.network.RemoteResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Wildan Nafian on 12/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

class SomeRemoteDataImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SomeRemoteData,
    BaseDataSource() {

    override suspend fun getSomeData(): RemoteResult<ResponseNewsApi> {
//        EspressoIdlingResource.increment()
        val data = withContext(ioDispatcher) {
            getResult { apiInterface.getAllNews() }
        }
//        EspressoIdlingResource.decrement()
        return data
    }
}