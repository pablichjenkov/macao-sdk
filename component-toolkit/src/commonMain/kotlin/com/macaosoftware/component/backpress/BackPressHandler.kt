package com.macaosoftware.component.backpress

import androidx.compose.runtime.*
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.ComponentLifecycleState

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
