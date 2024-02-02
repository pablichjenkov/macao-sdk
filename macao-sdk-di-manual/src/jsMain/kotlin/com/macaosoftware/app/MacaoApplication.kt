package com.macaosoftware.app

import androidx.compose.runtime.Composable
import com.macaosoftware.component.BrowserComponentRender

@Composable
fun MacaoApplication(
    onBackPress: () -> Unit,
    macaoApplicationState: MacaoApplicationState
) {

    macaoApplicationState.rootComponentState.value
        .takeIf { it != null }
        ?.also {
            BrowserComponentRender(
                rootComponent = it,
                onBackPress = onBackPress
            )
        }
        ?: {
            macaoApplicationState.fetchRootComponent()
        }
}
