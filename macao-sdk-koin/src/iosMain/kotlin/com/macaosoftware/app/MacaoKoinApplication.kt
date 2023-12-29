package com.macaosoftware.app

import androidx.compose.runtime.Composable
import com.macaosoftware.component.IosComponentRender
import com.macaosoftware.plugin.IosBridge
import com.macaosoftware.util.elseIfNull
import com.macaosoftware.util.ifNotNull

@Composable
fun MacaoKoinApplication(
    iosBridge: IosBridge,
    onBackPress: () -> Unit,
    applicationState: MacaoKoinApplicationState,
    splashScreenContent: @Composable () -> Unit
) {

    val rootComponent = applicationState.rootComponentState.value
    rootComponent.ifNotNull {
        IosComponentRender(
            rootComponent = it,
            iosBridge = iosBridge,
            onBackPress = onBackPress
        )
    }.elseIfNull {
        splashScreenContent()
        applicationState.fetchRootComponent()
    }
}
