package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.DesktopComponentRender
import com.macaosoftware.plugin.DesktopBridge
import com.macaosoftware.util.elseIfNull
import com.macaosoftware.util.ifNotNull

@Composable
fun JvmMacaoApplication(
    windowState: WindowState,
    desktopBridge: DesktopBridge,
    onBackPress: () -> Unit,
    macaoApplicationState: MacaoApplicationState,
    splashScreenContent: @Composable () -> Unit
) {

    val rootComponent = macaoApplicationState.rootComponentState.value
    rootComponent.ifNotNull {
        DesktopComponentRender(
            rootComponent = it,
            windowState = windowState,
            desktopBridge = desktopBridge,
            onBackPress = onBackPress
        )
    }.elseIfNull {
        splashScreenContent()
        macaoApplicationState.fetchRootComponent()
    }
}
