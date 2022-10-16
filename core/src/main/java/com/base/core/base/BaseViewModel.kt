package com.base.core.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren


/**
 * Created by Wildan Nafian on 05/08/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
open class BaseViewModel : ViewModel() {

    private val job = SupervisorJob()
    protected val mainScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCleared() {
        super.onCleared()
        mainScope.coroutineContext.cancelChildren()
    }
}