package com.base.myapplication.data.datasource.remote

import com.base.core.datasource.model.ResponseNewsApi
import com.base.core.datasource.network.ApiInterface
import com.base.core.datasource.network.BaseDataSource
import com.base.core.datasource.network.RemoteResult
import com.base.core.di.IoDispatcher
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
//        wrapEspressoIdlingResource {
            return withContext(ioDispatcher) {
                getResult { apiInterface.getAllNews() }
            }
//        }
    }
}