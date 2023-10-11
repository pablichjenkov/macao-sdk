package com.macaosoftware.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.macaosoftware.component.backpress.DefaultBackPressDispatcher
import com.macaosoftware.component.backpress.LocalBackPressedDispatcher
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.deeplink.LocalRootComponentProvider
import com.macaosoftware.platform.AppLifecycleEvent
import com.macaosoftware.platform.ForwardAppLifecycleCallback
import com.macaosoftware.platform.IosBridge
import platform.UIKit.UIViewController
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("IosComponentRender")
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
        rootComponent.dispatchAttach()
        rootComponent.isRoot = true
        rootComponent.rootBackPressDelegate = updatedOnBackPressed
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
