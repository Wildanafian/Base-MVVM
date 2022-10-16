package com.base.core.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext


/**
 * Created by Wildan Nafian on 05/08/2022.
 * Github https://github.com/Wildanafian
 * wildanafian8@gmail.com
 */
class FragmentLifecycleAwareCoroutine : CoroutineScope, LifecycleEventObserver {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_PAUSE) coroutineContext.cancelChildren()
    }

}