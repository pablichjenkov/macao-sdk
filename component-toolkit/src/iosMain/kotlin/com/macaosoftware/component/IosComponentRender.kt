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
import com.macaosoftware.plugin.Lifecycle

@Composable
fun IosComponentRender(
    rootComponent: Component
) {

    val lifecycle = remember(rootComponent) { Lifecycle() }

    CompositionLocalProvider(
        LocalRootComponentProvider provides rootComponent
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            rootComponent.Content(Modifier.fillMaxSize())
        }
    }

    LifecycleEventObserver(
        lifecycle = lifecycle,
        onStart = {
            println("Receiving IosApp.onStart() event")
            rootComponent.dispatchActive()
        },
        onStop = {
            println("Receiving IosApp.onStop() event")
            rootComponent.dispatchInactive()
        },
        initializeBlock = {
            rootComponent.dispatchAttach()
        }
    )

}
