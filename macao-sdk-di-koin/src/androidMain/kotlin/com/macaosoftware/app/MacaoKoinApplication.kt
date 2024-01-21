package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.macaosoftware.component.AndroidComponentRender

@Composable
fun MacaoKoinApplication(
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
            AndroidComponentRender(
                rootComponent = stage.rootComponent,
                onBackPress = onBackPress
            )
        }
    }
}
