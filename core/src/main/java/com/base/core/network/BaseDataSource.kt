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
                else RemoteResult.onError(response.code(), ResponseDataError(message = "body is null"))
            } else {
                val data = Gson().fromJson(response.errorBody()?.string(), ResponseDataError::class.java)
                RemoteResult.onError(response.code(), data)
            }
        } catch (e: Exception) {
            RemoteResult.onError(0, ResponseDataError(message = e.localizedMessage))
        }
    }
}

sealed class RemoteResult<out R> {
    data class onSuccess<out T>(val data: T) : RemoteResult<T>()
    data class onError(val code: Int, val dataError: ResponseDataError) : RemoteResult<Nothing>()
}

sealed class ConsumeResult<out R> {
    data class onSuccess<out T>(val data: T) : ConsumeResult<T>()
    data class onError(val errorMessage: String) : ConsumeResult<Nothing>()
    data class onLoading(val loading: Boolean) : ConsumeResult<Nothing>()
}
