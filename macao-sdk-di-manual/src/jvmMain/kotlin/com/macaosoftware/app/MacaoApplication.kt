package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.DesktopComponentRender
import com.macaosoftware.plugin.DesktopBridge

@Composable
fun MacaoApplication(
    windowState: WindowState,
    desktopBridge: DesktopBridge,
    onBackPress: () -> Unit,
    macaoApplicationState: MacaoApplicationState
) {

    macaoApplicationState.rootComponentState.value.takeIf { it != null }
        ?.also {
            DesktopComponentRender(
                rootComponent = it,
                windowState = windowState,
                desktopBridge = desktopBridge,
                onBackPress = onBackPress
            )
        }
        ?: {
            macaoApplicationState.fetchRootComponent()
        }
}
