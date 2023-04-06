package com.pablichj.incubator.uistate3.platform

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatchersProxy(
    val main: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val io: CoroutineDispatcher
) {
    companion object {
        val DefaultDispatchers: DispatchersProxy = DispatchersProxy(
            main = Dispatchers.Main,
            default = Dispatchers.Default,
            io = Dispatchers.Unconfined
        )
    }
}