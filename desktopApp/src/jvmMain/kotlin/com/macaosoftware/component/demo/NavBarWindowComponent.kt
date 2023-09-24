package com.macaosoftware.component.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.DesktopComponentRender
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.demo.viewmodel.BottomNavigationDemoViewModel
import com.macaosoftware.component.demo.viewmodel.factory.BottomNavigationDemoViewModelFactory
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.platform.DesktopBridge

class NavBarWindowComponent(
    val onCloseClick: () -> Unit
) : Component() {
    private val windowState = WindowState()
    private val desktopBridge = DesktopBridge()

    private var navBarComponent = NavBarComponent(
        // pushStrategy = FixSizedPushStrategy(1), // Uncomment to test other push strategies
        viewModelFactory = BottomNavigationDemoViewModelFactory(
            NavBarComponentDefaults.createNavBarStatePresenter()
        ),
        content = NavBarComponentDefaults.NavBarComponentView
    )

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
