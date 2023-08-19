package com.pablichj.templato.component.platform

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatchers(
    val main: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val io: CoroutineDispatcher
) {
    companion object {
        val Defaults = CoroutineDispatchers(
            main = Dispatchers.Main,
            default = Dispatchers.Default,
            io = Dispatchers.Unconfined
        )
    }
}
