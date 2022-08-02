package com.base.myapplication.data.repository.remote

import com.base.core.model.ResponseNewsApi
import com.base.core.network.RemoteResult

/**
 * Created by Wildan Nafian on 12/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

interface SomeRemoteData {
    suspend fun getSomeData(): RemoteResult<ResponseNewsApi>
}