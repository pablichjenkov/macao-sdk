package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.macaosoftware.component.AndroidComponentRender

@Composable
fun MacaoKoinApplication(
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
            AndroidComponentRender(
                rootComponent = stage.rootComponent
            )
        }
    }
}
