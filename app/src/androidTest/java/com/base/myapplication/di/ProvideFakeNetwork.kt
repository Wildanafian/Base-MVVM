package com.base.myapplication.di

import com.base.core.di.ProvideNetwork
import com.base.core.network.ApiInterface
import com.base.myapplication.helper.MockkWebServerConst.mockWebServer
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Wildan Nafian on 12/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ProvideNetwork::class]
)
object ProvideFakeNetwork {

    @Singleton
    @Provides
    fun provideApi(): ApiInterface {
        return Retrofit.Builder()
//            .baseUrl("http://127.0.0.1:8080/")
            .baseUrl(mockWebServer.url("/"))
//            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client())
            .build()
            .create(ApiInterface::class.java)
    }

    private fun client(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}