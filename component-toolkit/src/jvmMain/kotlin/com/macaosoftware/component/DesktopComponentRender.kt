package com.macaosoftware.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.util.LocalBackPressedDispatcher
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.deeplink.LocalRootComponentProvider
import com.macaosoftware.plugin.DesktopBridge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun DesktopComponentRender(
    rootComponent: Component,
    windowState: WindowState,
    desktopBridge: DesktopBridge,
    onBackPress: () -> Unit = {}
) {

    val desktopBackPressDispatcher = remember(rootComponent) {
        desktopBridge.backPressDispatcherPlugin
    }
    val updatedOnBackPressed by rememberUpdatedState(onBackPress)

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides desktopBackPressDispatcher,
        LocalRootComponentProvider provides rootComponent
    ) {
        rootComponent.Content(Modifier.fillMaxSize())
    }

    LaunchedEffect(rootComponent, windowState) {
        rootComponent.dispatchAttach()
        rootComponent.isRoot = true
        rootComponent.rootBackPressDelegate = updatedOnBackPressed

        launch {
            snapshotFlow { windowState.isMinimized }
                .onEach { isMinimized ->
                    onWindowMinimized(rootComponent, isMinimized)
                }
                .launchIn(this)
        }
    }
}

private fun onWindowMinimized(
    rootComponent: Component,
    minimized: Boolean
) {
    if (minimized) {
        rootComponent.dispatchStop()
    } else {
        rootComponent.dispatchStart()
    }
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
