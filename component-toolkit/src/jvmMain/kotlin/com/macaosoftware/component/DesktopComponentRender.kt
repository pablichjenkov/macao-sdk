package com.macaosoftware.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.deeplink.LocalRootComponentProvider
import com.macaosoftware.plugin.lifecycle.LifecycleEventObserver

@Composable
fun DesktopComponentRender(
    rootComponent: Component
) {

    CompositionLocalProvider(
        LocalRootComponentProvider provides rootComponent
    ) {
        rootComponent.Content(Modifier.fillMaxSize())
    }

    LifecycleEventObserver(
        lifecycleOwner = LocalLifecycleOwner.current,
        onStart = {
            println("Receiving Desktop.onStart() event")
            rootComponent.dispatchActive()
        },
        onStop = {
            println("Receiving Desktop.onStop() event")
            rootComponent.dispatchInactive()
        },
        initializeBlock = {
            rootComponent.dispatchAttach()
        }
    )
}

@Preview
@Composable
fun DesktopComponentRenderPreview() {
    val anonymousComponent = object : Component() {
        @Composable
        override fun Content(modifier: Modifier) {
            Column {
                Text(text = "Previewing an anonymous Component!")
            }
        }
    }
    anonymousComponent.Content(Modifier)
}
