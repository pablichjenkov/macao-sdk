package com.macaosoftware.component.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.DesktopComponentRender
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.factory.AppViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentDefaults
import com.macaosoftware.plugin.DesktopBridge

class FullAppWindowComponent(
    val onCloseClick: () -> Unit
) : Component() {
    private val windowState = WindowState(size = DpSize(800.dp, 900.dp))
    private var appComponent = StackComponent(
        viewModelFactory = AppViewModelFactory(
            stackStatePresenter = StackComponentDefaults.createStackStatePresenter(),
        ),
        content = StackComponentDefaults.DefaultStackComponentView
    )
    private val desktopBridge = DesktopBridge()

    @Composable
    override fun Content(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onCloseClick() }
        ) {
            DesktopComponentRender(
                rootComponent = appComponent,
                windowState = windowState,
                onBackPress = onCloseClick,
                desktopBridge = desktopBridge
            )
        }
    }

}