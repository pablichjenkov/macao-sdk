package com.macaosoftware.component.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.staticCompositionLocalOf
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.ComponentLifecycleState
import com.macaosoftware.plugin.BackPressDispatcher
import com.macaosoftware.plugin.DefaultBackPressDispatcher
import com.macaosoftware.plugin.ForwardBackPressCallback

/**
 * This [Composable] can be used with a [LocalBackPressedDispatcher] to intercept a back press.
 *
 * @param onBackPressed (Event) What to do when back is intercepted
 *
 */
@Composable
fun BackPressHandler(
    component: Component,
    enabled: Boolean = true,
    onBackPressed: () -> Unit
) {
    // Safely update the current `onBack` lambda when a new one is provided
    val currentOnBackPressed by rememberUpdatedState(onBackPressed)

    // Remember in Composition a back callback that calls the `onBackPressed` lambda
    val backPressCallback = remember(component) {
        ForwardBackPressCallback {
            currentOnBackPressed()
        }
        /* uncomment to test: https://github.com/JetBrains/compose-jb/issues/2615
        object : BackPressedCallback() {
            override fun onBackPressed() {
                currentOnBackPressed()
            }
        }*/
    }

    val backPressDispatcher = LocalBackPressedDispatcher.current
    val componentLifecycleState by component.lifecycleStateFlow.collectAsState(
        ComponentLifecycleState.Attached
    )

    when (componentLifecycleState) {
        ComponentLifecycleState.Attached -> {
            println("${component.instanceId()}::Lifecycle Flow = Created, Ignoring")
            // Ignore
        }

        ComponentLifecycleState.Started -> {
            println("${component.instanceId()}::Lifecycle Flow = Started, BackPressHandler Subscribing")
            if (enabled) {
                backPressDispatcher.subscribe(backPressCallback)
            }
        }

        ComponentLifecycleState.Stopped -> {
            println("${component.instanceId()}::Lifecycle Flow = Stopped, BackPressHandler Unsubscribing")
            backPressDispatcher.unsubscribe(backPressCallback)
        }

        ComponentLifecycleState.Detached -> {
            println("${component.instanceId()}::Lifecycle Flow = Destroyed, Ignoring")
            // Ignore it did unsubscribe in Stopped already.
        }
    }

}

/**
 * This [CompositionLocal] is used to provide an [BackPressDispatcher]:
 *
 * ```
 * val backPressedDispatcher = AndroidBackPressedDispatcher
 *
 * CompositionLocalProvider(
 *     LocalBackPressedDispatcher provides backPressedDispatcher
 * ) { }
 * ```
 *
 * and setting up the callbacks with [BackPressHandler].
 */
val LocalBackPressedDispatcher =
    staticCompositionLocalOf<BackPressDispatcher> { DefaultBackPressDispatcher() }
