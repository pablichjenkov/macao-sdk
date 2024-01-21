package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.macaosoftware.component.IosComponentRender
import com.macaosoftware.plugin.IosBridge

@Composable
fun MacaoKoinApplication(
    iosBridge: IosBridge,
    applicationState: MacaoKoinApplicationState,
    onBackPress: () -> Unit
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
            IosComponentRender(
                rootComponent = stage.rootComponent,
                iosBridge = iosBridge,
                onBackPress = onBackPress
            )
        }
    }
}
