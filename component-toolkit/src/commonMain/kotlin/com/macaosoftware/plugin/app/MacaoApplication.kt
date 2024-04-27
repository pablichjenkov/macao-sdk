package com.macaosoftware.plugin.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.macaosoftware.component.PlatformComponentRenderer

@Composable
fun MacaoApplication(
    applicationState: MacaoApplicationState
) = when (val stage = applicationState.stage.value) {

    Created -> {
        SideEffect {
            applicationState.initialize()
        }
    }

    is Initializing -> {
        InitializationHandler(stage)
    }

    is InitializationError -> {
        SplashScreen(stage.errorMsg)
    }

    is InitializationSuccess -> {
        PlatformComponentRenderer(rootComponent = stage.rootComponent)
    }
}

@Composable
private fun InitializationHandler(
    initializing: Initializing
) = when (initializing) {

    Initializing.PluginManager -> {
        // No-op
    }

    is Initializing.StartupTask -> {
        SplashScreen(initializing.taskName)
    }

    Initializing.RootComponent -> {
        SplashScreen("Fetching Root Component from Services")
    }
}

@Composable
private fun SplashScreen(text: String) {
    Box(Modifier.fillMaxSize().background(Color.LightGray)) {
        BasicText(
            modifier = Modifier.wrapContentSize().align(Alignment.Center),
            text = text
        )
    }
}
