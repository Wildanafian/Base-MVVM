package com.base.myapplication.di.dagger

import com.base.myapplication.data.repository.remote.SomeRemoteData
import com.base.myapplication.data.repository.remote.SomeRemoteDataImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */


@Module
interface ProvideRemoteData {

    @Binds
    fun provideRemoteData(useCase: SomeRemoteDataImpl) : SomeRemoteData
}