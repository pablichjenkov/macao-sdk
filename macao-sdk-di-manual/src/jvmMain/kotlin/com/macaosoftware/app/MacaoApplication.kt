package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.DesktopComponentRender

@Composable
fun MacaoApplication(
    windowState: WindowState,
    onBackPress: () -> Unit,
    macaoApplicationState: MacaoApplicationState
) {

    macaoApplicationState.rootComponentState.value.takeIf { it != null }
        ?.also {
            DesktopComponentRender(
                rootComponent = it,
                windowState = windowState,
                onBackPress = onBackPress
            )
        }
        ?: {
            macaoApplicationState.fetchRootComponent()
        }
}
