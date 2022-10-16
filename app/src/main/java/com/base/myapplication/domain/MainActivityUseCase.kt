package com.base.myapplication.domain

import com.base.core.datasource.model.ArticlesItem
import com.base.core.datasource.network.ConsumeResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */


interface MainActivityUseCase {
    fun getSomething(): Flow<ConsumeResult<List<ArticlesItem>>>
}