package com.base.myapplication

import android.app.Application
import com.base.myapplication.di.dagger.*
import com.base.myapplication.features.MainActivity
import dagger.Component
import timber.log.Timber
import javax.inject.Singleton

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

@Singleton
@Component(
    modules = [
        ProvideAppContext::class,
        CoroutineDispatchers::class,
        CoroutineScope::class,
        EncryptedSharedPreference::class,
        ProvideNetwork::class,
        ProvideRepository::class,
        ProvideSharedPreferences::class,
        ProvideUseCase::class,
        ProvideRemoteData::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}

class MainApplication : Application() {
    val appComponet: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .provideAppContext(ProvideAppContext(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}