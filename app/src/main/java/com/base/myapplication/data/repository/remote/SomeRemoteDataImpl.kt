package com.base.myapplication.data.repository.remote

import com.base.myapplication.data.repository.remote.network.ApiInterface
import com.base.myapplication.data.repository.remote.network.BaseDataSource
import com.base.myapplication.di.IoDispatcher
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

    override suspend fun getSomeData() = withContext(ioDispatcher) {
        getResult { apiInterface.getAllNews() }
    }

}