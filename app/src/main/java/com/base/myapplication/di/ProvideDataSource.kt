package com.base.myapplication.di

import com.base.myapplication.data.datasource.local.LocalSource
import com.base.myapplication.data.datasource.local.LocalSourceImpl
import com.base.myapplication.data.datasource.remote.SomeRemoteData
import com.base.myapplication.data.datasource.remote.SomeRemoteDataImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */


@Module
@InstallIn(SingletonComponent::class)
interface ProvideDataSource {

    @Binds
    fun provideLocalData(useCase: LocalSourceImpl): LocalSource

    @Binds
    fun provideRemoteData(useCase: SomeRemoteDataImpl): SomeRemoteData
}