package com.pablichj.templato.component.core

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.WindowState
import com.pablichj.templato.component.core.backpress.DefaultBackPressDispatcher
import com.pablichj.templato.component.core.backpress.LocalBackPressedDispatcher
import com.pablichj.templato.component.core.deeplink.LocalRootComponentProvider
import com.pablichj.templato.component.platform.DesktopBridge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun DesktopComponentRender(
    rootComponent: Component,
    windowState: WindowState,
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

    LaunchedEffect(rootComponent, windowState) {
        rootComponent.rootBackPressDelegate = updatedOnBackPressed
        launch {
            snapshotFlow { windowState.isMinimized }
                .onEach { isMinimized ->
                    onWindowMinimized(rootComponent, isMinimized)
                }
                .launchIn(this)
        }
    }
}

private fun onWindowMinimized(
    rootComponent: Component,
    minimized: Boolean
) {
    if (minimized) {
        rootComponent.dispatchStop()
    } else {
        rootComponent.dispatchStart()
    }
}
