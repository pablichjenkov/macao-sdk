package com.macaosoftware.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.deeplink.LocalRootComponentProvider
import com.macaosoftware.plugin.lifecycle.LifecycleEventObserver

@Composable
fun BrowserComponentRender(
    rootComponent: Component
) {

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
        lifecycleOwner = LocalLifecycleOwner.current,
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
