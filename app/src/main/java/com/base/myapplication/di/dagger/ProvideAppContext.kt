package com.base.myapplication.di.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Wildan Nafian on 26/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

@Module
class ProvideAppContext(private val context: Context) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context = context
}