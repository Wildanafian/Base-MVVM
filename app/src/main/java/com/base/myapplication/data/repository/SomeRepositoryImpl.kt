package com.base.myapplication.data.repository

import com.base.myapplication.data.model.ArticlesItem
import com.base.myapplication.data.repository.remote.SomeRemoteData
import com.base.myapplication.data.repository.remote.network.ConsumeResult
import com.base.myapplication.data.repository.remote.network.RemoteResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Wildan Nafian on 12/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

class SomeRepositoryImpl @Inject constructor(
    private val someRemoteData: SomeRemoteData
) :
    SomeRepository {

    //sample 1
    override fun getSomething(): Flow<ConsumeResult<List<ArticlesItem>>> {
        return flow {
            when (val data = someRemoteData.getSomeData()) {
                is RemoteResult.onSuccess -> emit(ConsumeResult.onSuccess(data.data.articles))
                is RemoteResult.onError -> emit(ConsumeResult.onError(Exception(data.e)))
            }
        }.catch {
            emit(ConsumeResult.onError(Exception(it)))
        }.flowOn(Dispatchers.IO)
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