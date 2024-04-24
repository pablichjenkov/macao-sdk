package com.macaosoftware.plugin.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.macaosoftware.component.PlatformComponentRenderer

@Composable
fun MacaoApplication(
    applicationState: MacaoApplicationState
) {

    when (val stage = applicationState.stage.value) {

        Stage.Created -> {
            SideEffect {
                applicationState.initialize()
            }
        }

        Stage.InitializingDiAndRootComponent -> {
            // Is up to the developer showing a full color screen or Splash screen
            // here in this stage. If the App requires fetching a configuration
            // from the network eg: server-driven-ui. Then show a loader animation here.
            // Otherwise just live it as is until the StartupCoordinator component
            // or root component start.
        }

        is Stage.Started -> {
            PlatformComponentRenderer(rootComponent = stage.rootComponent)
        }
    }
}
