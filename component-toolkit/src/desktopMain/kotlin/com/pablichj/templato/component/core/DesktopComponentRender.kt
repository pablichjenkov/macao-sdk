package com.pablichj.templato.component.core

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.pablichj.templato.component.core.backpress.DefaultBackPressDispatcher
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher
import com.pablichj.templato.component.core.router.LocalRootComponentProvider
import com.pablichj.templato.component.platform.AppLifecycleEvent
import com.pablichj.templato.component.platform.DesktopBridge
import com.pablichj.templato.component.platform.ForwardAppLifecycleCallback

@Composable
fun DesktopComponentRender(
    rootComponent: Component,
    desktopBridge: DesktopBridge,
    onBackPress: () -> Unit = {}
) {
    val desktopBackPressDispatcher = remember(rootComponent) {
        DefaultBackPressDispatcher()
    }
    val updatedOnBackPressed by rememberUpdatedState(onBackPress)

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides desktopBackPressDispatcher,
        LocalRootComponentProvider provides rootComponent
    ) {
        rootComponent.Content(Modifier.fillMaxSize())
        /*Box {
            rootComponent.Content(Modifier.fillMaxSize())
            // Add back button in the TopBar like Chrome Apps. For that need to create in
            FloatingBackButton(
                modifier = Modifier.offset(y = 48.dp),
                alignment = Alignment.TopStart,
                onClick = { desktopBackPressDispatcher.dispatchBackPressed() }
            )
        }*/
    }

    LaunchedEffect(key1 = rootComponent) {
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
