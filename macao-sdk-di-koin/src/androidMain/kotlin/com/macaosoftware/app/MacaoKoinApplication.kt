package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.macaosoftware.component.AndroidComponentRender

@Composable
fun MacaoKoinApplication(
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
            AndroidComponentRender(
                rootComponent = stage.rootComponent,
                onBackPress = onBackPress
            )
        }

        Stage.Starting -> {
            splashScreenContent()
        }
    }
}
