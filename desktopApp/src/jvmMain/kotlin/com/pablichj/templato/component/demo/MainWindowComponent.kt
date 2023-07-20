package com.pablichj.templato.component.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.DesktopComponentRender
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.getRouter
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.panel.PanelComponent
import com.pablichj.templato.component.core.router.DeepLinkMsg
import com.pablichj.templato.component.demo.treebuilders.AdaptableSizeTreeBuilder
import com.pablichj.templato.component.platform.AppLifecycleEvent
import com.pablichj.templato.component.platform.DefaultAppLifecycleDispatcher
import com.pablichj.templato.component.platform.DesktopBridge
import com.pablichj.templato.component.platform.DiContainer
import com.pablichj.templato.component.platform.DispatchersProxy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainWindowComponent(
    val onOpenDeepLinkClick: () -> Unit,
    val onRootNodeSelection: (WindowSample) -> Unit,
    val onExitClick: () -> Unit
) : Component() {
    private val windowState = WindowState(size = DpSize(400.dp, 900.dp))
    private var adaptableSizeComponent: Component
    private val appLifecycleDispatcher = DefaultAppLifecycleDispatcher()
    private val diContainer = DiContainer(DispatchersProxy.DefaultDispatchers)
    private val desktopBridge = DesktopBridge(
        appLifecycleDispatcher = appLifecycleDispatcher
    )

    init {
        val subtreeNavItems = AdaptableSizeTreeBuilder.getOrCreateDetachedNavItems()
        adaptableSizeComponent = AdaptableSizeTreeBuilder.build().also {
            it.setNavItems(subtreeNavItems, 0)
            it.setCompactContainer(
                DrawerComponent(
                    navigationDrawerState = DrawerComponent.createDefaultState(),
                    config = DrawerComponent.DefaultConfig,
                    diContainer = diContainer
                )
            )
            //it.setCompactContainer(PagerComponent())
            it.setMediumContainer(NavBarComponent())
            it.setExpandedContainer(PanelComponent(PanelComponent.createDefaultState()))
        }
    }

    // region: DeepLink

    fun handleDeepLink(destination: List<String>) {
        val deepLinkMsg = DeepLinkMsg(
            destination
        ) {
            println("MainWindowNode::deepLinkResult = $it")
        }
        adaptableSizeComponent.getRouter()?.handleDeepLink(deepLinkMsg)
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
                onBackPress = onExitClick,
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
