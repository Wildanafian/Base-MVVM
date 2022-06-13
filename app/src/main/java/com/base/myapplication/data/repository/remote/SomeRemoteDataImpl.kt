package com.base.myapplication.data.repository.remote

import android.annotation.SuppressLint
import com.base.myapplication.data.model.ResponseNewsApi
import com.base.myapplication.data.repository.remote.network.ApiInterface
import com.base.myapplication.data.repository.remote.network.BaseDataSource
import com.base.myapplication.data.repository.remote.network.RemoteResult
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

    override suspend fun getSomeData(): RemoteResult<ResponseNewsApi> =
        withContext(Dispatchers.IO) {
            getResult { apiInterface.getAllNews() }
        }

    override fun getSomeDataUsingRxJava(): Single<ResponseNewsApi> {
        return apiInterface.getAllNews2()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    @SuppressLint("CheckResult")
    fun someSample() {
        Single.zip(
            apiInterface.getAllNews2().subscribeOn(Schedulers.io()),
            apiInterface.getAllNews3().subscribeOn(Schedulers.io())
        ) { response1, response2 ->
            Pair(response1, response2)
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { (res1, res2) ->
                val asd = res1
                val zxc = res2
            }
    }
}