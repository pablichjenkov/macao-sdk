package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.macaosoftware.component.BrowserComponentRender
import com.macaosoftware.plugin.JsBridge

@Composable
fun MacaoKoinApplication(
    jsBridge: JsBridge,
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
            BrowserComponentRender(
                rootComponent = stage.rootComponent,
                jsBridge = jsBridge,
                onBackPress = onBackPress
            )
        }

        Stage.Starting -> {
            splashScreenContent()
        }
    }
}
