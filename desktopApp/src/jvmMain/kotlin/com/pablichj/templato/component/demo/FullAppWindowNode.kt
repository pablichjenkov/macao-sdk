package com.pablichj.templato.component.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.templato.component.platform.DesktopBridge
import com.pablichj.templato.component.core.DesktopComponentRender
import com.pablichj.templato.component.demo.treebuilders.FullAppWithIntroTreeBuilder
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.platform.AppLifecycleEvent
import com.pablichj.templato.component.platform.DefaultAppLifecycleDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FullAppWindowNode(
    val onCloseClick: () -> Unit
) : WindowNode {
    private val windowState = WindowState(size = DpSize(800.dp, 900.dp))
    private var activeComponent: Component = FullAppWithIntroTreeBuilder.build()
    private val appLifecycleDispatcher = DefaultAppLifecycleDispatcher()
    private val desktopBridge = DesktopBridge(
        appLifecycleDispatcher = appLifecycleDispatcher
    )

    @Composable
    override fun WindowContent(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onCloseClick() }
        ) {
            DesktopComponentRender(
                rootComponent = activeComponent,
                onBackPress = onCloseClick,
                desktopBridge = desktopBridge
            )
        }

        LaunchedEffect(windowState) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        onWindowMinimized(appLifecycleDispatcher, it)
                    }
                    .launchIn(this)
            }
        }
    }

    private fun onWindowMinimized(
        appLifecycleDispatcher: DefaultAppLifecycleDispatcher,
        minimized: Boolean
    ) {
        if (minimized) {
            appLifecycleDispatcher.dispatchAppLifecycleEvent(AppLifecycleEvent.Stop)
        } else {
            appLifecycleDispatcher.dispatchAppLifecycleEvent(AppLifecycleEvent.Start)
        }
    }

}