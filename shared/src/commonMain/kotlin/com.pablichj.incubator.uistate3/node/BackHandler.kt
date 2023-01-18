package com.pablichj.incubator.uistate3.node

import androidx.compose.runtime.*

/**
 * This [Composable] can be used with a [LocalBackPressedDispatcher] to intercept a back press.
 *
 * @param onBackPressed (Event) What to do when back is intercepted
 *
 */
@Composable
internal fun BackPressHandler(onBackPressed: () -> Unit) {
    // Safely update the current `onBack` lambda when a new one is provided
    val currentOnBackPressed by rememberUpdatedState(onBackPressed)

    // Remember in Composition a back callback that calls the `onBackPressed` lambda
    val backCallback = remember {
        ForwardBackPressCallback {
            currentOnBackPressed()
        }
    }

    val backDispatcher = LocalBackPressedDispatcher.current

    // Whenever there's a new dispatcher set up the callback
    DisposableEffect(backDispatcher) {
        println("Subscribing to backPressDispatcher, class = className here")
        backDispatcher.subscribe(backCallback)
        // When the effect leaves the Composition, or there's a new dispatcher, remove the callback
        onDispose {
            println("BackPressHandler::onDispose Unsubscribing from backPressDispatcher, class = className here")
            backDispatcher.unsubscribe(backCallback)
        }
    }
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
