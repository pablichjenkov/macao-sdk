package com.pablichj.incubator.uistate3.node

import androidx.compose.runtime.*

/**
 * This [Composable] can be used with a [LocalBackPressedDispatcher] to intercept a back press.
 *
 * @param onBackPressed (Event) What to do when back is intercepted
 *
 */
@Composable
internal fun BackPressHandler(
    node: Node,
    onBackPressed: () -> Unit
) {
    // Safely update the current `onBack` lambda when a new one is provided
    val currentOnBackPressed by rememberUpdatedState(onBackPressed)

    // Remember in Composition a back callback that calls the `onBackPressed` lambda
    val backPressCallback = remember {
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
    val nodeLifecycleState by node.nodeLifecycleFlow.collectAsState(Node.LifecycleState.Created)

    when (nodeLifecycleState) {
        Node.LifecycleState.Created -> {
            // Ignore
        }
        Node.LifecycleState.Started -> {
            println("${node.clazz}::onStart, BackPressHandler Subscribing")
            backPressDispatcher.subscribe(backPressCallback)
        }
        Node.LifecycleState.Stopped -> {
            println("${node.clazz}::onStop BackPressHandler Unsubscribing")
            backPressDispatcher.unsubscribe(backPressCallback)
        }
    }

    // Whenever there's a new dispatcher set up the callback
    /*DisposableEffect(backPressDispatcher) {
        println("Subscribing to backPressDispatcher, class = className here")
        backPressDispatcher.subscribe(backCallback)
        // When the effect leaves the Composition, or there's a new dispatcher, remove the callback
        onDispose {
            println("BackPressHandler::onDispose Unsubscribing from backPressDispatcher, class = className here")
            backPressDispatcher.unsubscribe(backCallback)
        }
    }*/
}

/**
 * This [CompositionLocal] is used to provide an [IBackPressDispatcher]:
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
internal val LocalBackPressedDispatcher =
    staticCompositionLocalOf<IBackPressDispatcher> { error("No Back Dispatcher provided") }
