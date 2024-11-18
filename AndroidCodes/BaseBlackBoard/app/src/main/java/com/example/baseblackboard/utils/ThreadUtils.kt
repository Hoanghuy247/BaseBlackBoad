package com.example.baseblackboard.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ThreadUtils {

    fun launchOnMain(action: CoroutineScope.() -> Unit) : Job {
        return launch(Dispatchers.Main, action, 0)
    }

    fun launchOnMainDelay(action: CoroutineScope.() -> Unit, delay: Long) : Job {
        return launch(Dispatchers.Main, action, delay)
    }

    fun launchOnIO(action: CoroutineScope.() -> Unit) : Job {
        return launch(Dispatchers.IO, action, 0)
    }

    fun launchOnIODelay(action: CoroutineScope.() -> Unit, delay: Long) : Job {
        return launch(Dispatchers.IO, action, delay)
    }

    fun launchSuspendOnMain(action: suspend CoroutineScope.() -> Unit) : Job {
        return launchSuspend(Dispatchers.Main, action, 0)
    }

    fun launchSuspendOnMainDelay(action: suspend CoroutineScope.() -> Unit, delay: Long) : Job {
        return launchSuspend(Dispatchers.Main, action, delay)
    }

    fun launchSuspendOnIO(action: suspend CoroutineScope.() -> Unit) : Job {
        return launchSuspend(Dispatchers.IO, action, 0)
    }

    fun launchSuspendOnIODelay(action: suspend CoroutineScope.() -> Unit, delay: Long) : Job {
        return launchSuspend(Dispatchers.Main, action, delay)
    }

    private fun launch(dispatcher: CoroutineDispatcher, action: CoroutineScope.() -> Unit, delay: Long) : Job {
        return CoroutineScope(dispatcher).launch {
            delay(delay)
            action
        }
    }

    private fun launchSuspend(dispatcher: CoroutineDispatcher,action: suspend CoroutineScope.() -> Unit, delay: Long) : Job {
        if (delay == 0L) {
            return CoroutineScope(dispatcher).launch(block = action)
        }
        return CoroutineScope(dispatcher).launch {
            delay(delay)
            action()
        }
    }
}