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
import com.pablichj.templato.component.core.router.DefaultRouter
import com.pablichj.templato.component.platform.AppLifecycleEvent
import com.pablichj.templato.component.platform.DesktopBridge
import com.pablichj.templato.component.platform.ForwardAppLifecycleCallback

@Composable
fun DesktopComponentRender(
    rootComponent: Component,
    onBackPress: () -> Unit = {},
    desktopBridge: DesktopBridge
) {
    val desktopBackPressDispatcher = remember(rootComponent) {
        DefaultBackPressDispatcher()
    }
    val router = remember(rootComponent) {
        DefaultRouter()
    }
    val updatedOnBackPressed by rememberUpdatedState(onBackPress)

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides desktopBackPressDispatcher,
        LocalRouter provides router
    ) {
        Box {
            rootComponent.Content(Modifier.fillMaxSize())
            // TODO: Add back button in the TopBar like Chrome Apps. For that need to create in
            // DesktopBridge a undecorated Window flag.
            /*FloatingBackButton(
                modifier = Modifier.offset(y = 48.dp),
                alignment = Alignment.TopStart,
                onClick = { desktopBackPressDispatcher.dispatchBackPressed() }
            )*/
        }
    }

    LaunchedEffect(key1 = rootComponent) {
        rootComponent.onBackPressDelegationReachRoot = updatedOnBackPressed
        desktopBridge.appLifecycleDispatcher.subscribe(
            ForwardAppLifecycleCallback {
                when (it) {
                    AppLifecycleEvent.Start -> rootComponent.dispatchStart()
                    AppLifecycleEvent.Stop -> rootComponent.dispatchStop()
                }
            }
        )
    }
}