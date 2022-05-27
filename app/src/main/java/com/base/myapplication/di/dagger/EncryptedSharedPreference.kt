package com.base.myapplication.di.dagger

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import java.security.KeyPairGenerator
import javax.inject.Singleton

/**
 * Created by Wildan Nafian on 12/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */

@Module
class EncryptedSharedPreference () {

    @Singleton
    @Provides
    fun provideInitSharedPreference(context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "YourName",
            createMasterKey(),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun createMasterKey(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        } else {
            val kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            kpg.initialize(
                KeyGenParameterSpec.Builder("alias", KeyProperties.PURPOSE_SIGN)
                    .setDigests(KeyProperties.DIGEST_SHA256)
                    .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PSS)
                    .build()
            )
            kpg.generateKeyPair().public.toString()
        }
    }
}