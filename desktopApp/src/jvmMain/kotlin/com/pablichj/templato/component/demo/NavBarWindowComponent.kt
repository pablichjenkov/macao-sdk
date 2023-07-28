package com.pablichj.templato.component.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.DesktopComponentRender
import com.pablichj.templato.component.demo.treebuilders.NavBarTreeBuilder
import com.pablichj.templato.component.platform.AppLifecycleEvent
import com.pablichj.templato.component.platform.DefaultAppLifecycleDispatcher
import com.pablichj.templato.component.platform.DesktopBridge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NavBarWindowComponent(
    val onCloseClick: () -> Unit
) : Component() {
    private val windowState = WindowState()
    private var navBarComponent: Component = NavBarTreeBuilder.build()
    private val desktopBridge = DesktopBridge()

    @Composable
    override fun Content(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onCloseClick() }
        ) {
            DesktopComponentRender(
                rootComponent = navBarComponent,
                windowState = windowState,
                onBackPress = onCloseClick,
                desktopBridge = desktopBridge
            )
        }
    }

}
