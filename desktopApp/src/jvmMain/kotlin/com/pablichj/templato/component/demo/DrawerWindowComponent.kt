package com.pablichj.templato.component.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.DesktopComponentRender
import com.pablichj.templato.component.demo.treebuilders.DrawerTreeBuilder
import com.macaosoftware.platform.DesktopBridge
import kotlin.system.exitProcess

class DrawerWindowComponent(
    val onCloseClick: () -> Unit
) : Component() {
    private val windowState = WindowState()
    private var drawerComponent: Component = DrawerTreeBuilder.build()
    private val desktopBridge = DesktopBridge()

    @Composable
    override fun Content(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onCloseClick() }
        ) {
            DesktopComponentRender(
                rootComponent = drawerComponent,
                windowState = windowState,
                onBackPress = { exitProcess(0) },
                desktopBridge = desktopBridge
            )
        }

    }

}
