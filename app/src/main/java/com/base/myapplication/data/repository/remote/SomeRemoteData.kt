package com.base.myapplication.data.repository.remote

import com.base.myapplication.data.model.ResponseNewsApi
import com.base.myapplication.data.repository.remote.network.RemoteResult
import io.reactivex.Single

/**
 * Created by Wildan Nafian on 12/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

interface SomeRemoteData {
    suspend fun getSomeData(): RemoteResult<ResponseNewsApi>
    fun getSomeDataUsingRxJava(): Single<ResponseNewsApi>
}