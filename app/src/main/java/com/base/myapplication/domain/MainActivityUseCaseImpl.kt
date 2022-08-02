package com.base.myapplication.domain

import com.base.core.model.ArticlesItem
import com.base.core.network.ConsumeResult
import com.base.myapplication.data.repository.SomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Wildan Nafian on 25/05/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */


class MainActivityUseCaseImpl @Inject constructor(private val repository: SomeRepository) :
    MainActivityUseCase {

    override fun getSomething(): Flow<ConsumeResult<List<ArticlesItem>>> {
        return repository.getSomething()
    }
}