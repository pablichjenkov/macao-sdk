package com.macaosoftware.component.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.DesktopComponentRender
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.factory.DrawerDemoViewModelFactory
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.platform.DesktopBridge
import kotlin.system.exitProcess

class DrawerWindowComponent(
    val onCloseClick: () -> Unit
) : Component() {
    private val windowState = WindowState()
    private val desktopBridge = DesktopBridge(onReady = {})

    private var drawerComponent: Component = DrawerComponent(
        viewModelFactory = DrawerDemoViewModelFactory(
            DrawerComponentDefaults.createDrawerStatePresenter()
        ),
        content = DrawerComponentDefaults.DrawerComponentView
    )

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
