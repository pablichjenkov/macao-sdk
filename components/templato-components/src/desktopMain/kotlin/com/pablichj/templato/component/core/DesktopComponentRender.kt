package com.pablichj.templato.component.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.TreeContext
import com.pablichj.templato.component.core.backpress.DefaultBackPressDispatcher
import com.pablichj.templato.component.core.backpress.ForwardBackPressCallback
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher
import com.pablichj.templato.component.core.dispatchAttachedToComponentTree
import com.pablichj.templato.component.platform.AppLifecycleEvent
import com.pablichj.templato.component.platform.DesktopBridge
import com.pablichj.templato.component.platform.ForwardAppLifecycleCallback

@Composable
fun DesktopComponentRender(
    rootComponent: Component,
    desktopBridge: DesktopBridge
) {
    val desktopBackPressDispatcher = remember(rootComponent) {
        DefaultBackPressDispatcher()
    }
    val treeContext = remember(rootComponent) {
        TreeContext()
    }
    val onBackPressEventHandler = desktopBridge.onBackPressEvent

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides desktopBackPressDispatcher
    ) {
        Box {
            rootComponent.Content(Modifier.fillMaxSize())
            // TODO: Add back button in the TopBar like Chrome Apps. For that need to create in
            // DesktopBridge a undecorated Window flag.
            /*FloatingBackButton(
                modifier = Modifier.offset(y = 48.dp),
                alignment = Alignment.TopStart,
                onClick = { desktopBackPressDispatcher.dispatchBackPressed() }
            )*/
        }
    }

    LaunchedEffect(key1 = rootComponent, key2 = onBackPressEventHandler) {
        rootComponent.rootBackPressedCallbackDelegate = ForwardBackPressCallback {
            onBackPressEventHandler()
        }
        // Traverse the whole tree passing the TreeContext living in the root node. Useful to
        // propagate the the Navigator for example. Where each Component interested in participating
        // in deep linking will subscribe its instance an a DeepLinkMatcher lambda function.
        println("DesktopComponentRender::dispatchAttachedToComponentTree")
        rootComponent.dispatchAttachedToComponentTree(treeContext)

        desktopBridge.appLifecycleDispatcher.subscribe(
            ForwardAppLifecycleCallback {
                when (it) {
                    AppLifecycleEvent.Start -> rootComponent.start()
                    AppLifecycleEvent.Stop -> rootComponent.stop()
                }
            }
        )
    }
}