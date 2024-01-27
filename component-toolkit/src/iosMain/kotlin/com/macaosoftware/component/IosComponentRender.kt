package com.macaosoftware.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.deeplink.LocalRootComponentProvider
import com.macaosoftware.component.util.LocalBackPressedDispatcher
import com.macaosoftware.plugin.DefaultBackPressDispatcherPlugin
import com.macaosoftware.plugin.LifecycleEventObserver
import com.macaosoftware.plugin.LifecycleOwner

@Composable
fun IosComponentRender(
    rootComponent: Component,
    onBackPress: () -> Unit = {}
) {

    val backPressDispatcher = remember(rootComponent) {
        // todo: get this from the plugin manager instead
        DefaultBackPressDispatcherPlugin()
    }

    val updatedOnBackPressed by rememberUpdatedState(onBackPress)
    val lifecycleOwner = remember(rootComponent) { LifecycleOwner() }

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides backPressDispatcher,
        LocalRootComponentProvider provides rootComponent
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            rootComponent.Content(Modifier.fillMaxSize())
        }
    }

    LifecycleEventObserver(
        lifecycleOwner = lifecycleOwner,
        onStart = {
            println("Receiving IosApp.onStart() event")
            rootComponent.dispatchStart()
        },
        onStop = {
            println("Receiving IosApp.onStop() event")
            rootComponent.dispatchStop()
        },
        initializeBlock = {
            rootComponent.dispatchAttach()
            rootComponent.rootBackPressDelegate = updatedOnBackPressed
        }
    )

}
