package com.macaosoftware.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.macaosoftware.component.IosComponentRender
import com.macaosoftware.plugin.IosBridge

@Composable
fun MacaoKoinApplication(
    iosBridge: IosBridge,
    applicationState: MacaoKoinApplicationState,
    onBackPress: () -> Unit,
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
            IosComponentRender(
                rootComponent = stage.rootComponent,
                iosBridge = iosBridge,
                onBackPress = onBackPress
            )
        }
    }
}
