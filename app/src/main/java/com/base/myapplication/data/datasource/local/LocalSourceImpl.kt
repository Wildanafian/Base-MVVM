package com.base.myapplication.data.datasource.local

import com.base.core.datasource.local.SharedPref
import com.base.core.datasource.model.NewsData
import com.base.core.util.ProjectConstant.NEWS
import com.base.core.util.fromJson
import com.base.core.util.toJson
import javax.inject.Inject


/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
class LocalSourceImpl @Inject constructor(private val sharedPreferences: SharedPref) :
    LocalSource {

    override fun cacheNews(data: List<NewsData>) {
        sharedPreferences.writeTo(NEWS, data.toJson())
    }

    override fun getNewsCache(key: String): List<NewsData> {
        return sharedPreferences.readString(NEWS).fromJson<List<NewsData>>() ?: emptyList()
    }

    override fun clearAllCacheData() {
        sharedPreferences.clearAllData()
    }
}