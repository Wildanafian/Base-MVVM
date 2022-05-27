package com.base.myapplication.di.dagger

import com.base.myapplication.data.repository.SomeRepository
import com.base.myapplication.data.repository.SomeRepositoryImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */


@Module
interface ProvideRepository {

    @Binds
    fun provideSomeRepository(repository: SomeRepositoryImpl): SomeRepository
}