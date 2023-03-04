package com.pablichj.incubator.uistate3.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.incubator.uistate3.DesktopComponentRender
import com.pablichj.incubator.uistate3.demo.treebuilders.AdaptableSizeTreeBuilder
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.adaptable.JvmWindowSizeInfoProvider
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.panel.PanelComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainWindowNode(
    val onOpenDeepLinkClick: () -> Unit,
    val onRootNodeSelection: (WindowNodeSample) -> Unit,
    val onExitClick: () -> Unit
) : WindowNode {
    private val windowState = WindowState(size = DpSize(800.dp, 900.dp))

    // todo: get this from a compositionlocalprovider
    private val windowSizeInfoProvider = JvmWindowSizeInfoProvider(windowState)
    private var adaptableSizeComponent: Component

    init {
        val subtreeNavItems = AdaptableSizeTreeBuilder.getOrCreateDetachedNavItems()

        adaptableSizeComponent = AdaptableSizeTreeBuilder.build(
            windowSizeInfoProvider
        ).also {
            it.setNavItems(subtreeNavItems, 0)
            it.setCompactContainer(DrawerComponent())
            it.setMediumContainer(NavBarComponent())
            it.setExpandedContainer(PanelComponent())
        }
    }

    // region: DeepLink

    fun handleDeepLink(destination: String) {
        val deepLinkResult = adaptableSizeComponent
            .treeContext
            ?.navigator
            ?.handleDeepLink(destination)
        println("MainWindowNode::deepLinkResult = ${deepLinkResult}")
    }

    // endregion

    @Composable
    override fun WindowContent(modifier: Modifier) {
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
                            onRootNodeSelection(WindowNodeSample.Drawer)
                        }
                    )
                    Item(
                        "Nav Bottom Bar",
                        onClick = {
                            onRootNodeSelection(WindowNodeSample.Navbar)
                        }
                    )
                    Item(
                        "Left Panel",
                        onClick = {
                            onRootNodeSelection(WindowNodeSample.Panel)
                        }
                    )
                    Item(
                        "Full App Sample",
                        onClick = {
                            onRootNodeSelection(WindowNodeSample.FullApp)
                        }
                    )
                }

            }
            DesktopComponentRender(
                rootComponent = adaptableSizeComponent,
                onBackPressEvent = {
                    //exitProcess(0)
                }
            )
        }

        LaunchedEffect(windowState) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        onWindowMinimized(adaptableSizeComponent, it)
                    }
                    .launchIn(this)
            }
        }

    }

    private fun onWindowMinimized(activeComponent: Component, minimized: Boolean) {
        if (minimized) {
            activeComponent.stop()
        } else {
            activeComponent.start()
            //val jsonText = adaptableSizeComponent.jsonify()
            //println("MainWindowNode::jsonText: $jsonText")
        }
    }

}