package com.base.myapplication.data.repository.remote

import com.base.myapplication.data.repository.remote.network.ApiInterface
import com.base.myapplication.data.repository.remote.network.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Wildan Nafian on 12/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

class SomeRemoteDataImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : SomeRemoteData,
    BaseDataSource() {

    override suspend fun getSomeData() = withContext(Dispatchers.IO) {
        getResult { apiInterface.getAllNews() }
    }

}