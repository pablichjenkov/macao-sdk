package com.macaosoftware.plugin.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.macaosoftware.component.AndroidComponentRender
import com.macaosoftware.plugin.app.MacaoApplicationState
import com.macaosoftware.plugin.app.Stage

@Composable
fun MacaoApplication(
    applicationState: MacaoApplicationState
) {

    when (val stage = applicationState.stage.value) {

        Stage.Created -> {
            SideEffect {
                applicationState.start()
            }
        }

        Stage.Loading -> {
        }

        is Stage.Started -> {
            AndroidComponentRender(
                rootComponent = stage.rootComponent
            )
        }
    }
}
