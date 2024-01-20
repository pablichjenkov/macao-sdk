package com.macaosoftware.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.DesktopComponentRender
import com.macaosoftware.plugin.DesktopBridge

@Composable
fun MacaoKoinApplication(
    windowState: WindowState,
    desktopBridge: DesktopBridge,
    onBackPress: () -> Unit,
    applicationState: MacaoKoinApplicationState,
    screenColorWhileKoinLoads: Color? = null
) {

    when (val stage = applicationState.stage.value) {

        Stage.Created -> {
            SideEffect {
                applicationState.start()
            }
        }

        Stage.KoinLoading -> {
            screenColorWhileKoinLoads?.let {
                Box(modifier = Modifier.fillMaxSize().background(color = it))
            }
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
