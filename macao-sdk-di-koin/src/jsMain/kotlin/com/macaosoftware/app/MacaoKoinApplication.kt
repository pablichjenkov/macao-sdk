package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.macaosoftware.component.BrowserComponentRender

@Composable
fun MacaoKoinApplication(
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
            BrowserComponentRender(
                rootComponent = stage.rootComponent,
                onBackPress = onBackPress
            )
        }
    }
}
