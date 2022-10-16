package com.base.core.di

import com.base.core.datasource.local.SharedPref
import com.base.core.datasource.local.SharedPrefImpl
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
interface ProvideSharedPreferences {

    @Binds
    fun provideSharedPreferances(sharedPref: SharedPrefImpl) : SharedPref
}