package com.base.myapplication.di.dagger

import com.base.myapplication.domain.MainActivityUseCase
import com.base.myapplication.domain.MainActivityUseCaseImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */


@Module
interface ProvideUseCase {

    @Binds
    fun provideMainActivityUseCase(useCase: MainActivityUseCaseImpl) : MainActivityUseCase
}