package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.macaosoftware.component.BrowserComponentRender

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
            BrowserComponentRender(rootComponent = stage.rootComponent)
        }
    }
    
}
