package com.macaosoftware.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.deeplink.LocalRootComponentProvider
import com.macaosoftware.plugin.Lifecycle
import com.macaosoftware.plugin.LifecycleEventObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun DesktopComponentRender(
    rootComponent: Component,
    windowState: WindowState,
    onBackPress: () -> Unit = {}
) {

    val updatedOnBackPressed by rememberUpdatedState(onBackPress)

    val lifecycle = remember(rootComponent) {
        Lifecycle(CoroutineScope(Dispatchers.Main), windowState)
    }

    CompositionLocalProvider(
        LocalRootComponentProvider provides rootComponent
    ) {
        rootComponent.Content(Modifier.fillMaxSize())
    }

    LifecycleEventObserver(
        lifecycle = lifecycle,
        onStart = {
            println("Receiving Desktop.onStart() event")
            rootComponent.dispatchActive()
        },
        onStop = {
            println("Receiving Desktop.onStop() event")
            rootComponent.dispatchInactive()
        },
        initializeBlock = {
            rootComponent.dispatchAttach()
            rootComponent.rootBackPressDelegate = updatedOnBackPressed
        }
    )
}

@Preview
@Composable
fun DesktopComponentRenderPreview() {
    val anonymousComponent = object : Component() {
        @Composable
        override fun Content(modifier: Modifier) {
            Column {
                Text(text = "Previewing an anonymous Component!")
            }
        }
    }
    anonymousComponent.Content(Modifier)
}
