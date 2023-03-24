package com.pablichj.incubator.uistate3.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.incubator.uistate3.DesktopBridge
import com.pablichj.incubator.uistate3.DesktopComponentRender
import com.pablichj.incubator.uistate3.demo.treebuilders.NavBarTreeBuilder
import com.pablichj.incubator.uistate3.node.*
import com.pablichj.incubator.uistate3.platform.AppLifecycleEvent
import com.pablichj.incubator.uistate3.platform.DefaultAppLifecycleDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NavBarWindowNode(
    val onCloseClick: () -> Unit
) : WindowNode {
    private val windowState = WindowState()
    private var navBarComponent: Component = NavBarTreeBuilder.build()
    private val appLifecycleDispatcher = DefaultAppLifecycleDispatcher()
    private val desktopBridge = DesktopBridge(
        appLifecycleDispatcher = appLifecycleDispatcher,
        onBackPressEvent = onCloseClick
    )

    @Composable
    override fun WindowContent(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onCloseClick() }
        ) {
            DesktopComponentRender(
                rootComponent = navBarComponent,
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