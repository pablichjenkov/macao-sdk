package com.pablichj.incubator.amadeus.common

import kotlinx.coroutines.*

fun CoroutineScope.launchCatching(
    onException: (e: Exception) -> Unit,
    onClean: () -> Unit = {},
    body: suspend CoroutineScope.() -> Unit
) {
    launch {
        try {
            body()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            onException(e)
        } finally {
            withContext(NonCancellable) {
                onClean()
            }
        }
    }
}