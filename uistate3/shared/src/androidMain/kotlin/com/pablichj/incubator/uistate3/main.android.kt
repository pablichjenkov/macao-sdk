package com.pablichj.incubator.uistate3

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.pablichj.incubator.uistate3.node.AndroidBackPressDispatcher
import com.pablichj.incubator.uistate3.node.backstack.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.backstack.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.Component

@Composable
fun AndroidNodeRender(
    rootComponent: Component,
    onBackPressEvent: () -> Unit = {}
) {
    LaunchedEffect(key1 = rootComponent, key2 = onBackPressEvent) {
        rootComponent.rootBackPressedCallbackDelegate = ForwardBackPressCallback {
            onBackPressEvent()
        }
    }

    LifecycleEventObserver(
        lifecycleOwner = LocalLifecycleOwner.current,
        onStart = {
            println("Pablo Receiving Activity.onStart() event")
            if (rootComponent.lifecycleState != Component.LifecycleState.Started) {
                rootComponent.start()
            }
        },
        onStop = {
            println("Pablo Receiving Activity.onStop() event")
            if (rootComponent.lifecycleState != Component.LifecycleState.Stopped) {
                rootComponent.stop()
            }
        }
    )

    val activity = LocalContext.current as ComponentActivity

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides AndroidBackPressDispatcher(activity)
    ) {
        rootComponent.Content(Modifier.fillMaxSize())
    }

}