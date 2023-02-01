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
import com.pablichj.incubator.uistate3.DesktopNodeRender
import com.pablichj.incubator.uistate3.demo.treebuilders.AdaptableSizeTreeBuilder
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.JvmWindowSizeInfoProvider
import com.pablichj.incubator.uistate3.node.Node
import com.pablichj.incubator.uistate3.node.drawer.DrawerNode
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import com.pablichj.incubator.uistate3.node.navigation.Path
import com.pablichj.incubator.uistate3.node.navigation.SubPath
import com.pablichj.incubator.uistate3.node.panel.PanelNode
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainWindowNode(
    val onOpenDeepLinkClick: () -> Unit,
    val onRootNodeSelection: (WindowNodeSample) -> Unit,
    val onExitClick: () -> Unit
) : WindowNode {
    private val windowState = WindowState(size = DpSize(800.dp, 900.dp))

    // todo: get this from a compositionlocalprovider
    private val windowSizeInfoProvider = JvmWindowSizeInfoProvider(windowState)
    private val defaultBackPressDispatcher = DefaultBackPressDispatcher()
    private var AdaptableSizeNode: Node

    init {
        //this@MainWindowNode.context.subPath = SubPath("App")
        val subtreeNavItems = AdaptableSizeTreeBuilder.getOrCreateDetachedNavItems()

        AdaptableSizeNode = AdaptableSizeTreeBuilder.build(
            windowSizeInfoProvider
        ).also {
            it.subPath = SubPath("AdaptableWindow")
            it.setNavItems(subtreeNavItems, 0)
            it.setCompactContainer(DrawerNode().apply { subPath = SubPath("Drawer") })
            it.setMediumContainer(NavBarNode().apply { subPath = SubPath("Navbar") })
            it.setExpandedContainer(PanelNode().apply { subPath = SubPath("Panel") })
        }
    }

    // region: DeepLink

    fun handleDeepLink(path: Path) {}

    /*override */fun getDeepLinkNodes(): List<Node> {
        return listOf(AdaptableSizeNode)
    }

    /*override */ fun onDeepLinkMatchingNode(matchingNode: Node) {
        println("MainWindowNode.onDeepLinkMatchingNode() matchingNode = ${matchingNode.subPath}")
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

            /*CompositionLocalProvider(
                LocalBackPressedDispatcher provides defaultBackPressDispatcher,
            ){
                Box {
                    AdaptableSizeNode.Content(Modifier)
                    FloatingButton(
                        modifier = Modifier.offset(y = 48.dp),
                        alignment = Alignment.TopStart,
                        onClick = { defaultBackPressDispatcher.dispatchBackPressed() }
                    )

                }
            }*/

            DesktopNodeRender(
                rootNode = AdaptableSizeNode,
                onBackPressEvent = { exitProcess(0) }
            )

        }

        LaunchedEffect(windowState) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        onWindowMinimized(AdaptableSizeNode, it)
                    }
                    .launchIn(this)
            }
        }

    }

    private fun onWindowMinimized(activeNode: Node, minimized: Boolean) {
        if (minimized) {
            activeNode.stop()
        } else {
            activeNode.start()
        }
    }

}