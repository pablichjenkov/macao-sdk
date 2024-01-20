package com.macaosoftware.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.macaosoftware.component.AndroidComponentRender

@Composable
fun MacaoKoinApplication(
    onBackPress: () -> Unit,
    applicationState: MacaoKoinApplicationState,
    screenColorWhileKoinLoads: Color? = null
) {

    when (val stage = applicationState.stage.value) {

        Stage.Created -> {
            SideEffect {
                applicationState.start()
            }
        }

        Stage.KoinLoading -> {
            screenColorWhileKoinLoads?.let {
                Box(modifier = Modifier.fillMaxSize().background(color = it))
            }
        }

        is Stage.Started -> {
            AndroidComponentRender(
                rootComponent = stage.rootComponent,
                onBackPress = onBackPress
            )
        }
    }
}
