package com.macaosoftware.app

import androidx.compose.runtime.Composable
import com.macaosoftware.component.BrowserComponentRender
import com.macaosoftware.plugin.JsBridge

@Composable
fun MacaoApplication(
    jsBridge: JsBridge,
    onBackPress: () -> Unit,
    macaoApplicationState: MacaoApplicationState
) {

    macaoApplicationState.rootComponentState.value
        .takeIf { it != null }
        ?.also {
            BrowserComponentRender(
                rootComponent = it,
                jsBridge = jsBridge,
                onBackPress = onBackPress
            )
        }
        ?: {
            macaoApplicationState.fetchRootComponent()
        }
}
