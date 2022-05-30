package com.base.myapplication.data.repository.remote.network

import com.base.myapplication.data.model.BaseResponse
import com.base.myapplication.data.model.ResponseNewsApi
import com.base.myapplication.data.repository.remote.network.ApiEndPoint
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */


interface ApiInterface {

    @POST(ApiEndPoint.GET_PROFILE)
    @FormUrlEncoded
    suspend fun getProfile(@Field("data") data: String?): Response<BaseResponse>

    @GET("everything")
    suspend fun getAllNews(
        @Query("q") q: String? = "tesla",
        @Query("page") page: Int? = 1,
        @Query("apiKey") apiKey: String? = "a5bf56b6153c4ee9bd64368cba3e1317"
    ): Response<ResponseNewsApi>

    @GET("everything")
    suspend fun getAllNews2(
        @Query("q") q: String? = "tesla",
        @Query("page") page: Int? = 1,
        @Query("apiKey") apiKey: String? = "a5bf56b6153c4ee9bd64368cba3e1317"
    ): Observable<ResponseNewsApi>

}