package com.macaosoftware.plugin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun LifecycleEventObserver(
    lifecycle: Lifecycle,
    onStart: () -> Unit,
    onStop: () -> Unit,
    initializeBlock: () -> Unit
) {
    // Safely update the current lambdas when a new one is provided
    val currentOnStart by rememberUpdatedState(newValue = onStart)
    val currentOnStop by rememberUpdatedState(newValue = onStop)

    // If the enclosing lifecycleOwner changes, dispose and reset the effect
    DisposableEffect(key1 = initializeBlock, key2 = lifecycle) {
        initializeBlock.invoke()
        // Create an observer that triggers our remembered callbacks
        // when the LifecycleOwner that contains this composable changes its state.
        val observer = object : AppLifecycleCallback {
            override fun onEvent(appLifecycleEvent: AppLifecycleEvent) {
                if (appLifecycleEvent == AppLifecycleEvent.Start) {
                    currentOnStart()
                } else if (appLifecycleEvent == AppLifecycleEvent.Stop) {
                    currentOnStop()
                }
            }
        }

        // Add the observer to the lifecycle
        lifecycle.subscribe(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycle.unsubscribe(observer)
            println(
                "LifecycleEventObserver::Disposing LifecycleEventObserver"
            )
        }
    }

}
