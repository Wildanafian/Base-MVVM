package com.base.myapplication.data.repository

import com.base.core.datasource.model.ArticlesItem
import com.base.core.datasource.network.ConsumeResult
import com.base.core.datasource.network.RemoteResult
import com.base.core.di.IoDispatcher
import com.base.myapplication.data.datasource.remote.SomeRemoteData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import java.net.SocketException
import javax.inject.Inject

/**
 * Created by Wildan Nafian on 12/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

class SomeRepositoryImpl @Inject constructor(
    private val someRemoteData: SomeRemoteData,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) :
    SomeRepository {

    //sample 1
    override fun getSomething(): Flow<ConsumeResult<List<ArticlesItem>>> {
        return flow {
            when (val data = someRemoteData.getSomeData()) {
                is RemoteResult.onSuccess -> emit(ConsumeResult.onSuccess(data.data.articles))
                is RemoteResult.onError -> emit(ConsumeResult.onError(data.dataError.message ?: ""))
            }
        }.retryWhen { cause, attempt ->
            cause is SocketException && attempt < 2
        }.catch {
            emit(ConsumeResult.onError(it.localizedMessage ?: ""))
        }.flowOn(ioDispatcher)
    }

    //sample 2
//    override fun getSomething(): Flow<ConsumeResult<List<ArticlesItem>>> {
//        return flow {
//            when(val data = someRemoteData.getSomeData()){
//                is RemoteResult.onSuccess -> {
//                    emit(ConsumeResult.onSuccess(data.data.articles))
//                    emit(ConsumeResult.onLoading(false))
//                }
//                is RemoteResult.onError -> {
//                    emit(ConsumeResult.onError(Exception(data.e)))
//                    emit(ConsumeResult.onLoading(false))
//                }
//            }
//        }.onStart {
//            emit(ConsumeResult.onLoading(true))
//        }.catch {
//            it.printLog()
//            emit(ConsumeResult.onError(Exception(it)))
//            emit(ConsumeResult.onLoading(false))
//        }.flowOn(Dispatchers.IO)
//    }


}