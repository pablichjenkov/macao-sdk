package com.pablichj.templato.component.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.backpress.DefaultBackPressDispatcher
import com.pablichj.templato.component.core.backpress.ForwardBackPressCallback
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher

@Composable
fun BrowserComponentRender(
    rootComponent: Component,
    onBackPressEvent: () -> Unit
) {
    val webBackPressDispatcher = remember(rootComponent) {
        DefaultBackPressDispatcher()
    }
    val updatedOnBackPressed by rememberUpdatedState(onBackPressEvent)

    val internalRootComponent = remember(key1 = rootComponent) {
        InternalRootComponent(
            platformRootComponent = rootComponent,
            onBackPressEvent = { updatedOnBackPressed.invoke() }
        )
    }

    LaunchedEffect(key1 = rootComponent) {
        internalRootComponent.dispatchStart()
    }

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides webBackPressDispatcher,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            internalRootComponent.Content(Modifier.fillMaxSize())
            /* Should listen for keyboard back instead
            FloatingBackButton(
                modifier = Modifier.offset(y = 48.dp),
                alignment = Alignment.TopStart,
                onClick = { webBackPressDispatcher.dispatchBackPressed() }
            )*/
        }
    }
}
