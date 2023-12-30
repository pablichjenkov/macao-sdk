package com.macaosoftware.app

import androidx.compose.runtime.Composable
import com.macaosoftware.component.BrowserComponentRender
import com.macaosoftware.plugin.JsBridge
import com.macaosoftware.util.elseIfNull
import com.macaosoftware.util.ifNotNull

@Composable
fun MacaoKoinApplication(
    jsBridge: JsBridge,
    onBackPress: () -> Unit,
    applicationState: MacaoKoinApplicationState,
    splashScreenContent: @Composable () -> Unit
) {

    val rootComponent = applicationState.rootComponentState.value
    rootComponent.ifNotNull {
        BrowserComponentRender(
            rootComponent = it,
            jsBridge = jsBridge,
            onBackPress = onBackPress
        )
    }.elseIfNull {
        splashScreenContent()
        applicationState.fetchRootComponent()
    }
}
