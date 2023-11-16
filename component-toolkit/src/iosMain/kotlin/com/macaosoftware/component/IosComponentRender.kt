package com.macaosoftware.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.macaosoftware.component.util.LocalBackPressedDispatcher
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.deeplink.LocalRootComponentProvider
import com.macaosoftware.plugin.AppLifecycleEvent
import com.macaosoftware.plugin.DefaultBackPressDispatcherPlugin
import com.macaosoftware.plugin.ForwardAppLifecycleCallback
import com.macaosoftware.plugin.IosBridge

@Composable
fun IosComponentRender(
    rootComponent: Component,
    iosBridge: IosBridge,
    onBackPress: () -> Unit = {}
) {

    val backPressDispatcher = remember(rootComponent) {
        // todo: get this from the plugin manager instead
        DefaultBackPressDispatcherPlugin()
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
        iosBridge.platformLifecyclePlugin.subscribe(
            ForwardAppLifecycleCallback {
                when (it) {
                    AppLifecycleEvent.Start -> rootComponent.dispatchStart()
                    AppLifecycleEvent.Stop -> rootComponent.dispatchStop()
                }
            }
        )
    }

}
