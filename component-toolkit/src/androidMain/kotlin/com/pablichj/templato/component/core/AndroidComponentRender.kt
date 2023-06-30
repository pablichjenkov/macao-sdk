package com.pablichj.templato.component.core

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.pablichj.templato.component.core.backpress.AndroidBackPressDispatcher
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher

@Composable
fun AndroidComponentRender(
    rootComponent: Component,
    onBackPressEvent: () -> Unit = {}
) {
    val updatedOnBackPressed by rememberUpdatedState(onBackPressEvent)

    val internalRootComponent = remember(key1 = rootComponent) {
        InternalRootComponent(
            platformRootComponent = rootComponent,
            onBackPressEvent = { updatedOnBackPressed.invoke() }
        )
    }

    LifecycleEventObserver(
        lifecycleOwner = LocalLifecycleOwner.current,
        onStart = {
            println("Receiving Activity.onStart() event")
            internalRootComponent.dispatchStart()
        },
        onStop = {
            println("Receiving Activity.onStop() event")
            internalRootComponent.dispatchStop()
        }
    )

    val activity = LocalContext.current as ComponentActivity

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides AndroidBackPressDispatcher(activity)
    ) {
        internalRootComponent.Content(Modifier.fillMaxSize())
    }

}
