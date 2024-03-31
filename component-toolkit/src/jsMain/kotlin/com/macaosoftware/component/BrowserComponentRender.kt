package com.macaosoftware.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.deeplink.LocalRootComponentProvider
import com.macaosoftware.plugin.Lifecycle
import com.macaosoftware.plugin.LifecycleEventObserver

@Composable
fun BrowserComponentRender(
    rootComponent: Component
) {

    val lifecycle = remember(rootComponent) { Lifecycle() }

    CompositionLocalProvider(
        LocalRootComponentProvider provides rootComponent
    ) {
        rootComponent.Content(Modifier.fillMaxSize())
        /*Box(modifier = Modifier.fillMaxSize()) {
            // Should listen for keyboard back instead
            FloatingBackButton(
                modifier = Modifier.offset(y = 48.dp),
                alignment = Alignment.TopStart,
                onClick = { webBackPressDispatcher.dispatchBackPressed() }
            )
        }*/
    }

    LifecycleEventObserver(
        lifecycle = lifecycle,
        onStart = {
            println("Receiving Js.onStart() event")
            rootComponent.dispatchActive()
        },
        onStop = {
            println("Receiving Js.onStop() event")
            rootComponent.dispatchInactive()
        },
        initializeBlock = {
            rootComponent.dispatchAttach()
        }
    )

}
