package com.base.myapplication.data.repository.local.sharedpreference


/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
interface SharedPref {

    fun writeTo(key: String, data: String?)
    fun writeTo(key: String, data: Int?)
    fun writeTo(key: String, data: Boolean)

    fun readString(key: String): String
    fun readInt(key: String): Int
    fun readBoolean(key: String): Boolean

    fun clearAllData()
    fun clearByKey(key: String)

    //////optional example///////
    fun clearLoginData()

}