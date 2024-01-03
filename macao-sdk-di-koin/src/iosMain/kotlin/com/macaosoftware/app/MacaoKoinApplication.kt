package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.macaosoftware.component.IosComponentRender
import com.macaosoftware.plugin.IosBridge

@Composable
fun MacaoKoinApplication(
    iosBridge: IosBridge,
    onBackPress: () -> Unit,
    applicationState: MacaoKoinApplicationState,
    splashScreenContent: @Composable () -> Unit
) {

    when (val stage = applicationState.stage.value) {
        Stage.Created -> {
            SideEffect {
                applicationState.start()
            }
        }

        is Stage.Started -> {
            IosComponentRender(
                rootComponent = stage.rootComponent,
                iosBridge = iosBridge,
                onBackPress = onBackPress
            )
        }

        Stage.Starting -> {
            splashScreenContent()
        }
    }
}
