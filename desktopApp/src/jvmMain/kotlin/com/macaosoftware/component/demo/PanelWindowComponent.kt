package com.macaosoftware.component.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.DesktopComponentRender
import com.macaosoftware.component.demo.treebuilders.PanelTreeBuilder
import com.macaosoftware.platform.DesktopBridge

class PanelWindowComponent(
    val onCloseClick: () -> Unit
) : Component() {
    private val windowState = WindowState()
    private var panelComponent: Component = PanelTreeBuilder.build()
    private val desktopBridge = DesktopBridge()

    @Composable
    override fun Content(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onCloseClick() }
        ) {
            DesktopComponentRender(
                rootComponent = panelComponent,
                windowState = windowState,
                onBackPress = onCloseClick,
                desktopBridge = desktopBridge
            )
        }
    }

}
