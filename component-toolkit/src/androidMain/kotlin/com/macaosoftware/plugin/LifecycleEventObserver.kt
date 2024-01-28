package com.macaosoftware.plugin

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun LifecycleEventObserver(
    lifecycleOwner: LifecycleOwner,
    onStart: () -> Unit,
    onStop: () -> Unit,
    initializeBlock: () -> Unit
) {
    // Safely update the current lambdas when a new one is provided
    val currentOnStart by rememberUpdatedState(newValue = onStart)
    val currentOnStop by rememberUpdatedState(newValue = onStop)

    // If the enclosing lifecycleOwner changes, dispose and reset the effect
    DisposableEffect(key1 = initializeBlock, key2 = lifecycleOwner) {
        initializeBlock.invoke()
        // Create an observer that triggers our remembered callbacks
        // when the LifecycleOwner that contains this composable changes its state.
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            Log.d(
                "LifecycleEventObserver",
                "Disposing LifecycleEventObserver"
            )
        }
    }

}