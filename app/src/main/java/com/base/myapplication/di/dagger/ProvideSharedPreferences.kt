package com.base.myapplication.di.dagger

import com.base.myapplication.data.repository.local.sharedpreference.SharedPref
import com.base.myapplication.data.repository.local.sharedpreference.SharedPrefImpl
import dagger.Binds
import dagger.Module


/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

@Module
interface ProvideSharedPreferences {

    @Binds
    fun provideSharedPreferances(sharedPref: SharedPrefImpl) : SharedPref
}