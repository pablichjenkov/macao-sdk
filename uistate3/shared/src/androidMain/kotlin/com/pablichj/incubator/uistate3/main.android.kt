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
import com.pablichj.incubator.uistate3.node.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.Node

@Composable
fun AndroidNodeRender(
    rootNode: Node,
    onBackPressEvent: () -> Unit = {}
) {
    LaunchedEffect(key1 = rootNode, key2 = onBackPressEvent) {
        rootNode.context.rootNodeBackPressedDelegate = ForwardBackPressCallback {
            onBackPressEvent()
        }
    }

    LifecycleEventObserver(
        lifecycleOwner = LocalLifecycleOwner.current,
        onStart = {
            println("Pablo Receiving Activity.onStart() event")
            if (rootNode.context.lifecycleState != Node.LifecycleState.Started) {
                rootNode.start()
            }
        },
        onStop = {
            println("Pablo Receiving Activity.onStop() event")
            if (rootNode.context.lifecycleState != Node.LifecycleState.Stopped) {
                rootNode.stop()
            }
        }
    )

    val activity = LocalContext.current as ComponentActivity

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides AndroidBackPressDispatcher(activity)
    ) {
        rootNode.Content(Modifier.fillMaxSize())
    }

}