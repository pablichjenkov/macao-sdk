package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.DesktopComponentRender

@Composable
fun MacaoKoinApplication(
    windowState: WindowState,
    onBackPress: () -> Unit,
    applicationState: MacaoKoinApplicationState
) {

    when (val stage = applicationState.stage.value) {

        KoinAppStage.Created -> {
            SideEffect {
                applicationState.start()
            }
        }

        KoinAppStage.Loading -> {
        }

        is KoinAppStage.Started -> {
            DesktopComponentRender(
                rootComponent = stage.rootComponent,
                windowState = windowState,
                onBackPress = onBackPress
            )
        }
    }
}
