package com.base.myapplication.data.repository.local.sharedpreference

import android.content.SharedPreferences
import com.base.myapplication.data.repository.local.sharedpreference.SharedPref
import javax.inject.Inject


/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
class SharedPrefImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    SharedPref {

    override fun writeTo(key: String, data: String?) {
        sharedPreferences.edit().putString(key, data).apply()
    }

    override fun writeTo(key: String, data: Int?) {
        if (data != null) sharedPreferences.edit().putInt(key, data).apply()
    }

    override fun writeTo(key: String, data: Boolean) {
        sharedPreferences.edit().putBoolean(key, data).apply()
    }

    override fun readString(key: String): String {
        return sharedPreferences.getString(key, "") ?: ""
    }

    override fun readInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    override fun readBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    override fun clearAllData() {
        sharedPreferences.edit().clear().apply()
    }

    override fun clearByKey(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun clearLoginData() {
//        sharedPreferences.edit().remove("login").apply()
//        sharedPreferences.edit().remove("profile").apply()
//        sharedPreferences.edit().remove("data").apply()
    }

}