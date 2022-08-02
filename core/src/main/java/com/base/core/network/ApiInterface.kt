package com.base.core.network

import com.base.core.BuildConfig
import com.base.core.model.BaseResponse
import com.base.core.model.ResponseNewsApi
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @POST(ApiEndPoint.GET_PROFILE)
    @FormUrlEncoded
    suspend fun getProfile(@Field("data") data: String?): Response<BaseResponse>

    @GET("everything")
    suspend fun getAllNews(
        @Query("q") q: String? = "tesla",
        @Query("page") page: Int? = 1,
        @Query("apiKey") apiKey: String? = BuildConfig.APIKey
    ): Response<ResponseNewsApi>

}