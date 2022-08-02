package com.base.core.network

import com.google.gson.Gson
import com.base.core.model.ResponseDataError
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): RemoteResult<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) RemoteResult.onSuccess(body)
                else RemoteResult.onError(Exception("body is null"))
            } else {
                val data =
                    Gson().fromJson(response.errorBody()?.string(), ResponseDataError::class.java)
                RemoteResult.onError(Exception("${response.code()} : ${data.message}"))
            }
        } catch (e: Exception) {
            RemoteResult.onError(e)
        }
    }
}

sealed class RemoteResult<out R> {
    class onSuccess<out T>(val data: T) : RemoteResult<T>()
    class onError(val e: Exception) : RemoteResult<Nothing>()
}

sealed class ConsumeResult<out R> {
    class onSuccess<out T>(val data: T) : ConsumeResult<T>()
    class onError(val e: Exception) : ConsumeResult<Nothing>()
    class onLoading(val loading: Boolean) : ConsumeResult<Nothing>()
}
