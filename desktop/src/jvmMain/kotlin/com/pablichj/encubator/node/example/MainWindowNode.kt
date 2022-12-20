package com.pablichj.encubator.node.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.encubator.node.*
import com.pablichj.encubator.node.drawer.DrawerNode
import com.pablichj.encubator.node.navbar.NavBarNode
import com.pablichj.encubator.node.navigation.SubPath
import com.pablichj.encubator.node.panel.PanelNode
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainWindowNode(
    parentContext: NodeContext,
    val onOpenDeepLinkClick: () -> Unit,
    val onExitClick: () -> Unit
) : Node(parentContext) {
    private val windowState = WindowState()
    private val windowSizeInfoProvider = JvmWindowSizeInfoProvider(windowState)

    // todo: Replace isActive by onCloseCallback
    private var isActive = mutableStateOf(true)
    private var activeNode: Node

    init {
        this@MainWindowNode.context.subPath = SubPath("App")
        val subtreeNavItems = AdaptableWindowTreeBuilder.getOrCreateDetachedNavItems()

        activeNode = AdaptableWindowTreeBuilder.build(
            windowSizeInfoProvider,
            JvmBackPressDispatcher(),
            backPressedCallback = object : BackPressedCallback() {
                override fun onBackPressed() {}
            }
        ).apply {
            this@apply.context.subPath = SubPath("AdaptableWindow")
            setNavItems(subtreeNavItems, 0)
            setCompactNavigator(DrawerNode(context).apply { context.subPath = SubPath("Drawer") })
            setMediumNavigator(NavBarNode(context).apply { context.subPath = SubPath("Navbar") })
            setExpandedNavigator(PanelNode(context).apply { context.subPath = SubPath("Panel") })
        }

    }

    // region: DeepLink

    override fun getDeepLinkNodes(): List<Node> {
        return listOf(activeNode)
    }

    override fun onDeepLinkMatchingNode(matchingNode: Node) {
        println("OnboardingNode.onDeepLinkMatchingNode() matchingNode = ${matchingNode.context.subPath}")
        //activeNode.
    }

    // endregion

    @Composable
    override fun Content(modifier: Modifier) {
        if (!isActive.value) {
            return
        }

        Window(
            state = windowState,
            onCloseRequest = {
                isActive.value = false
            }
        ) {
            MenuBar {
                Menu("File") {
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
            }
            activeNode.Content(Modifier)
        }

        LaunchedEffect(windowState) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        onWindowMinimized(activeNode, it)
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