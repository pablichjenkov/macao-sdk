package com.macaosoftware.component.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.DesktopComponentRender
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.factory.PanelDemoViewModelFactory
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.macaosoftware.platform.DesktopBridge

class PanelWindowComponent(
    val onCloseClick: () -> Unit
) : Component() {
    private val windowState = WindowState()
    private val desktopBridge = DesktopBridge()

    private var panelComponent = PanelComponent(
        viewModelFactory = PanelDemoViewModelFactory(
            PanelComponentDefaults.createPanelStatePresenter()
        ),
        content = PanelComponentDefaults.PanelComponentView
    )

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
