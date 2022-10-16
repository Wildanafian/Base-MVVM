package com.base.myapplication.data.datasource.local

import com.base.core.datasource.model.NewsData


/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
interface LocalSource {

    fun cacheNews(data: List<NewsData>)

    fun getNewsCache(key: String): List<NewsData>

    fun clearAllCacheData()
}
