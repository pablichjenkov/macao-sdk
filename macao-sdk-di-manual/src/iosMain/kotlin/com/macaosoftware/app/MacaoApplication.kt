package com.macaosoftware.app

import androidx.compose.runtime.Composable
import com.macaosoftware.component.IosComponentRender

@Composable
fun MacaoApplication(
    onBackPress: () -> Unit,
    applicationState: MacaoApplicationState
) {

    applicationState.rootComponentState.value
        .takeIf { it != null }
        ?.also {
            IosComponentRender(
                rootComponent = it,
                onBackPress = onBackPress
            )
        }
        ?: {
            applicationState.fetchRootComponent()
        }
}
