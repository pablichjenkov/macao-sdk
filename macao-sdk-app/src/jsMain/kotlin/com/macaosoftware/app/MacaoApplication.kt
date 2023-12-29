package com.macaosoftware.app

import androidx.compose.runtime.Composable
import com.macaosoftware.component.BrowserComponentRender
import com.macaosoftware.plugin.JsBridge
import com.macaosoftware.util.elseIfNull
import com.macaosoftware.util.ifNotNull

@Composable
fun MacaoApplication(
    jsBridge: JsBridge,
    onBackPress: () -> Unit,
    macaoApplicationState: MacaoApplicationState,
    splashScreenContent: @Composable () -> Unit
) {

    val rootComponent = macaoApplicationState.rootComponentState.value
    rootComponent.ifNotNull {
        BrowserComponentRender(
            rootComponent = it,
            jsBridge = jsBridge,
            onBackPress = onBackPress
        )
    }.elseIfNull {
        splashScreenContent()
        macaoApplicationState.fetchRootComponent()
    }
}
