package com.pablichj.incubator.uistate3

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.pablichj.incubator.uistate3.node.AndroidBackPressDispatcher
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.TreeContext
import com.pablichj.incubator.uistate3.node.backstack.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.backstack.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.dispatchAttachedToComponentTree

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

    val treeContext = remember(rootComponent) {
        TreeContext()
    }

    LifecycleEventObserver(
        lifecycleOwner = LocalLifecycleOwner.current,
        onStart = {
            println("Receiving Activity.onStart() event")
            // Traverse the whole tree passing the TreeContext living in the root node. Useful to
            // propagate the the Navigator for example. Where each Component interested in participating
            // in deep linking will subscribe its instance an a DeepLinkMatcher lambda function.
            println("AndroidComponentRender::dispatchAttachedToComponentTree")
            rootComponent.dispatchAttachedToComponentTree(treeContext)
            rootComponent.start()
        },
        onStop = {
            println("Receiving Activity.onStop() event")
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