package com.pablichj.incubator.uistate3

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.pablichj.incubator.uistate3.node.AndroidBackPressDispatcher
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.backstack.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.backstack.LocalBackPressedDispatcher

@Composable
fun AndroidComponentRender(
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
            rootComponent.start()
        },
        onStop = {
            println("Pablo Receiving Activity.onStop() event")
            rootComponent.stop()
        }
    )

    val activity = LocalContext.current as ComponentActivity

    MaterialTheme {
        CompositionLocalProvider(
            LocalBackPressedDispatcher provides AndroidBackPressDispatcher(activity)
        ) {
            rootComponent.Content(Modifier.fillMaxSize())
        }
    }

}