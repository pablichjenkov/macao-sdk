package com.macaosoftware.component.demo

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.macaosoftware.component.DesktopComponentRender
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.deeplink.DeepLinkMsg
import com.macaosoftware.component.core.deeplink.DefaultDeepLinkManager
import com.macaosoftware.component.demo.componentDelegates.Demo3PageTopBarViewModel
import com.macaosoftware.component.demo.componentDelegates.PanelComponentDelegate1
import com.macaosoftware.component.demo.treebuilders.AdaptableSizeTreeBuilder
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults
import com.macaosoftware.platform.DesktopBridge

class MainWindowComponent(
    val onOpenDeepLinkClick: () -> Unit,
    val onRootNodeSelection: (WindowSample) -> Unit,
    val onExitClick: () -> Unit
) : Component() {
    private val windowState = WindowState(size = DpSize(1000.dp, 900.dp))
    private var adaptableSizeComponent: Component
    private val desktopBridge = DesktopBridge()

    init {
        val navItems = AdaptableSizeTreeBuilder.getOrCreateDetachedNavItems()
        adaptableSizeComponent = AdaptableSizeTreeBuilder.build().also {
            it.setNavItems(navItems, 0)
            it.setCompactContainer(
                DrawerComponent(
                    drawerStatePresenter = DrawerComponentDefaults.createDrawerStatePresenter(),
                    componentViewModel = DrawerComponentDefaults.createViewModel(),
                    content = DrawerComponentDefaults.DrawerComponentView
                )
            )
            //it.setCompactContainer(PagerComponent())
            it.setMediumContainer(
                NavBarComponent(
                    navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
                    componentViewModel = NavBarComponentDefaults.createViewModel(),
                    content = NavBarComponentDefaults.NavBarComponentView
                )
            )
            it.setExpandedContainer(
                PanelComponent(
                    panelStatePresenter = PanelComponentDefaults.createPanelStatePresenter(),
                    componentDelegate = PanelComponentDelegate1(),
                    content = PanelComponentDefaults.PanelComponentView
                )
            )
        }
    }

    // region: DeepLink

    fun handleDeepLink(destinations: List<String>) {
        val deepLinkMsg = DeepLinkMsg(
            path = destinations,
            resultListener = { result ->
                println("MainWindowNode::deepLinkResult = $result")
            }
        )
        DefaultDeepLinkManager().navigateToDeepLink(adaptableSizeComponent, deepLinkMsg)
    }

    // endregion

    @Composable
    override fun Content(modifier: Modifier) {
        Window(
            state = windowState,
            onCloseRequest = { onExitClick() }
        ) {
            MenuBar {
                Menu("Actions") {
                    Item(
                        "Deep Link",
                        onClick = {
                            onOpenDeepLinkClick()
                        }
                    )
                    Item(
                        "Exit",
                        onClick = {
                            onExitClick()
                        }
                    )
                }
                Menu("Samples") {
                    Item(
                        "Slide Drawer",
                        onClick = {
                            onRootNodeSelection(WindowSample.Drawer)
                        }
                    )
                    Item(
                        "Nav Bottom Bar",
                        onClick = {
                            onRootNodeSelection(WindowSample.Navbar)
                        }
                    )
                    Item(
                        "Left Panel",
                        onClick = {
                            onRootNodeSelection(WindowSample.Panel)
                        }
                    )
                    Item(
                        "Full App Sample",
                        onClick = {
                            onRootNodeSelection(WindowSample.FullApp)
                        }
                    )
                }
            }
            DesktopComponentRender(
                rootComponent = adaptableSizeComponent,
                windowState = windowState,
                onBackPress = onExitClick,
                desktopBridge = desktopBridge
            )
        }
    }

}

@Preview
@Composable
fun MainWindowComponentPreview() {

    /*
    val simpleComponent = SimpleComponent(
        "Simple",
        Color.Yellow,
    ) {}
    simpleComponent.Content(Modifier)
    */

    val topbarComponent = TopBarComponent(
        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
        componentViewModel = Demo3PageTopBarViewModel.create(
            "Orders",
            {}
        ),
        content = TopBarComponentDefaults.TopBarComponentView
    ).apply {
        uriFragment = "Orders"
    }
    topbarComponent.dispatchStart()
    topbarComponent.Content(Modifier)
}
