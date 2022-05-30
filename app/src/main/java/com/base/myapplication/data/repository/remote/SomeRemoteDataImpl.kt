package com.base.myapplication.data.repository.remote

import com.base.myapplication.data.model.ArticlesItem
import com.base.myapplication.data.model.ResponseNewsApi
import com.base.myapplication.data.repository.remote.network.ApiInterface
import com.base.myapplication.data.repository.remote.network.BaseDataSource
import com.base.myapplication.data.repository.remote.network.ConsumeResult
import com.base.myapplication.data.repository.remote.network.RemoteResult
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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

    suspend fun getSomeDataUsingRxJava(): ConsumeResult<List<ArticlesItem>> {
        var asd: ConsumeResult<List<ArticlesItem>>
        asd = ConsumeResult.onLoading(true)
        apiInterface.getAllNews2()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ResponseNewsApi> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(data: ResponseNewsApi) {
                    asd = ConsumeResult.onSuccess(data.articles)
                }

                override fun onError(e: Throwable) {
                    asd = ConsumeResult.onError(e as Exception)
                }

                override fun onComplete() {
                    asd = ConsumeResult.onLoading(false)
                }
            }
            )
        return asd
    }

}