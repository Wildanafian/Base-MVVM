package com.base.myapplication.di.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.base.myapplication.features.MainActivityViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


/**
 * Created by Wildan Nafian on 27/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindFirstFragmentViewModel(fragmentViewModel: MainActivityViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)