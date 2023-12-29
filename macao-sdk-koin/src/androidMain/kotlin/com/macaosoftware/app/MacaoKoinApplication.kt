package com.macaosoftware.app

import androidx.compose.runtime.Composable
import com.macaosoftware.component.AndroidComponentRender
import com.macaosoftware.util.elseIfNull
import com.macaosoftware.util.ifNotNull

@Composable
fun MacaoKoinApplication(
    onBackPress: () -> Unit,
    applicationState: MacaoKoinApplicationState,
    splashScreenContent: @Composable () -> Unit
) {

    val rootComponent = applicationState.rootComponentState.value
    rootComponent.ifNotNull {
        AndroidComponentRender(
            rootComponent = it,
            onBackPress = onBackPress
        )
    }.elseIfNull {
        splashScreenContent()
        applicationState.fetchRootComponent()
    }
}
