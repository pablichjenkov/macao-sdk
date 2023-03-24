package com.pablichj.incubator.uistate3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.TreeContext
import com.pablichj.incubator.uistate3.node.backpress.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.backpress.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.backpress.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.dispatchAttachedToComponentTree
import com.pablichj.incubator.uistate3.platform.AppLifecycleCallback
import com.pablichj.incubator.uistate3.platform.AppLifecycleEvent
import com.pablichj.incubator.uistate3.platform.ForwardAppLifecycleCallback
import com.pablichj.incubator.uistate3.platform.LocalSafeAreaInsets
import platform.UIKit.UIViewController

fun IosComponentRender(
    rootComponent: Component,
    iosBridge: IosBridge
): UIViewController = ComposeUIViewController {

    val backPressDispatcher = remember {
        DefaultBackPressDispatcher()
    }

    val treeContext = remember(rootComponent) {
        TreeContext()
    }

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides backPressDispatcher,
        LocalSafeAreaInsets provides iosBridge.safeAreaInsets
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            rootComponent.Content(Modifier.fillMaxSize())
        }
    }

    LaunchedEffect(key1 = rootComponent/*, key2 = onBackPressEvent*/) {
        rootComponent.rootBackPressedCallbackDelegate = ForwardBackPressCallback {
            //onBackPressEvent()
            println("back pressed dispatched in root node")
        }
        // Traverse the whole tree passing the TreeContext living in the root node. Useful to
        // propagate the the Navigator for example. Where each Component interested in participating
        // in deep linking will subscribe its instance an a DeepLinkMatcher lambda function.
        println("IosComponentRender::dispatchAttachedToComponentTree")
        rootComponent.dispatchAttachedToComponentTree(treeContext)

        iosBridge.appLifecycleDispatcher.subscribe(
            ForwardAppLifecycleCallback {
                println("IosComponentRender::onEvent(${it}) does nothing")
                when (it) {
                    AppLifecycleEvent.Start -> rootComponent.start()
                    AppLifecycleEvent.Stop -> rootComponent.stop()
                }
            }
        )
    }

}