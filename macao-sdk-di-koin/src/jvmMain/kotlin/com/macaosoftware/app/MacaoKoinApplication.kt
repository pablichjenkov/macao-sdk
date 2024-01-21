package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.DesktopComponentRender
import com.macaosoftware.plugin.DesktopBridge

@Composable
fun MacaoKoinApplication(
    windowState: WindowState,
    desktopBridge: DesktopBridge,
    onBackPress: () -> Unit,
    applicationState: MacaoKoinApplicationState
) {

    when (val stage = applicationState.stage.value) {

        Stage.Created -> {
            SideEffect {
                applicationState.start()
            }
        }

        Stage.KoinLoading -> {
        }

        is Stage.Started -> {
            DesktopComponentRender(
                rootComponent = stage.rootComponent,
                windowState = windowState,
                desktopBridge = desktopBridge,
                onBackPress = onBackPress
            )
        }
    }
}
