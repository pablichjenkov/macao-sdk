package com.pablichj.templato.component.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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

    // TODO: Accept a callback for back press in the constructor
    // val updatedOnBackPressed by rememberUpdatedState(onBackPressEvent)

    val internalRootComponent = remember(key1 = rootComponent) {
        InternalRootComponent(
            platformRootComponent = rootComponent,
            onBackPressEvent = {
                println("back pressed dispatched in root node")
                /*updatedOnBackPressed.invoke()*/
            }
        )
    }

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides backPressDispatcher,
        LocalSafeAreaInsets provides iosBridge.safeAreaInsets
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            internalRootComponent.Content(Modifier.fillMaxSize())
        }
    }

    LaunchedEffect(key1 = rootComponent/*, key2 = onBackPressEvent*/) {
        iosBridge.appLifecycleDispatcher.subscribe(
            ForwardAppLifecycleCallback {
                when (it) {
                    AppLifecycleEvent.Start -> internalRootComponent.dispatchStart()
                    AppLifecycleEvent.Stop -> internalRootComponent.dispatchStop()
                }
            }
        )
    }

}
