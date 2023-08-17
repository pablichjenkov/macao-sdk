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
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher
import com.pablichj.templato.component.core.deeplink.LocalRootComponentProvider
import com.pablichj.templato.component.platform.AppLifecycleEvent
import com.pablichj.templato.component.platform.ForwardAppLifecycleCallback
import com.pablichj.templato.component.platform.IosBridge
import platform.UIKit.UIViewController

fun IosComponentRender(
    rootComponent: Component,
    iosBridge: IosBridge,
    onBackPress: () -> Unit = {}
): UIViewController = ComposeUIViewController {

    val backPressDispatcher = remember(rootComponent) {
        DefaultBackPressDispatcher()
    }

    val updatedOnBackPressed by rememberUpdatedState(onBackPress)

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides backPressDispatcher,
        LocalRootComponentProvider provides rootComponent
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            rootComponent.Content(Modifier.fillMaxSize())
        }
    }

    LaunchedEffect(key1 = rootComponent) {
        iosBridge.appLifecycleDispatcher.subscribe(
            ForwardAppLifecycleCallback {
                when (it) {
                    AppLifecycleEvent.Start -> rootComponent.dispatchStart()
                    AppLifecycleEvent.Stop -> rootComponent.dispatchStop()
                }
            }
        )
    }

}
