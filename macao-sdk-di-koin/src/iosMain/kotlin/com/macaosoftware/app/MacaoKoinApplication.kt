package com.macaosoftware.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.macaosoftware.component.IosComponentRender

@Composable
fun MacaoKoinApplication(
    applicationState: MacaoKoinApplicationState,
    onBackPress: () -> Unit
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
            IosComponentRender(
                rootComponent = stage.rootComponent,
                onBackPress = onBackPress
            )
        }
    }
}
