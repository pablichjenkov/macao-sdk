package com.pablichj.templato.component.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.pablichj.templato.component.core.backpress.DefaultBackPressDispatcher
import com.pablichj.templato.component.core.backpress.ForwardBackPressCallback
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher
import com.pablichj.templato.component.platform.AppLifecycleEvent
import com.pablichj.templato.component.platform.ForwardAppLifecycleCallback
import com.pablichj.templato.component.platform.IosBridge
import com.pablichj.templato.component.platform.LocalSafeAreaInsets
import platform.UIKit.UIViewController

fun IosComponentRender(
    rootComponent: Component,
    iosBridge: IosBridge
): UIViewController = ComposeUIViewController {

    val backPressDispatcher = remember(rootComponent) {
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
            //onBackPressEvent()//todo: Place it in the platform bridge
            println("back pressed dispatched in root node")
        }

        // Traverse the whole tree passing the TreeContext living in the root node. Useful to
        // propagate the the Navigator for example. Where each Component interested in participating
        // in deep linking will subscribe its instance an a DeepLinkMatcher lambda function.
        println("IosComponentRender::dispatchAttachedToComponentTree")
        rootComponent.dispatchAttachedToComponentTree(treeContext)

        iosBridge.appLifecycleDispatcher.subscribe(
            ForwardAppLifecycleCallback {
                when (it) {
                    AppLifecycleEvent.Start -> rootComponent.start()
                    AppLifecycleEvent.Stop -> rootComponent.stop()
                }
            }
        )
    }

}