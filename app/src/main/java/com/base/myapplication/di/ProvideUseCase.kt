package com.base.myapplication.di

import com.base.myapplication.domain.MainActivityUseCase
import com.base.myapplication.domain.MainActivityUseCaseImpl
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
interface ProvideUseCase {

    @Binds
    fun provideMainActivityUseCase(useCase: MainActivityUseCaseImpl) : MainActivityUseCase
}