package com.pablichj.encubator.node.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import com.pablichj.encubator.node.*
import com.pablichj.encubator.node.drawer.DrawerNode
import com.pablichj.encubator.node.navbar.NavBarNode
import com.pablichj.encubator.node.panel.PanelNode
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainWindowNode(
    parentContext: NodeContext,
    val onCreateWindowClick: () -> Unit
) : WindowNode(parentContext) {

    var isActive = mutableStateOf(true)

    private var activeNode: Node

    init {

        val subtreeNavItems = AdaptableWindowTreeBuilder.getOrCreateDetachedNavItems()

        activeNode = AdaptableWindowTreeBuilder.build(
            // TODO: JvmWindowSizeInfoProvider has to be provided from the top window
            windowSizeInfoProvider,//JvmWindowSizeInfoProvider(windowState),
            JvmBackPressDispatcher(),
            backPressedCallback = object : BackPressedCallback() {
                override fun onBackPressed() {}
            }
        ).apply {
            setNavItems(subtreeNavItems, 0)
            setCompactNavigator(DrawerNode(context).apply { context.subPath = SubPath("Drawer") })
            setMediumNavigator(NavBarNode(context).apply { context.subPath = SubPath("Navbar") })
            setExpandedNavigator(PanelNode(context).apply { context.subPath = SubPath("Panel") })
        }

    }

    @Composable
    override fun Content(modifier: Modifier) {

        if (!isActive.value) {
            return
        }

        Window(
            onCloseRequest = {
                isActive.value = false
            }/*::exitApplication*/,
            windowState
        ) {
            MenuBar {
                Menu("File") {
                    Item(
                        "Deep Link",
                        onClick = {
                            activeNode.handleDeepLink(getDeepLinkPath())
                        }
                    )
                    Item(
                        "New window",
                        onClick = {
                            onCreateWindowClick()
                            /*state.openNewWindow*/
                        }
                    )
                    Item("Exit", onClick = {/*state.exit*/ })
                }
            }
            activeNode.Content(Modifier)
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

    }

    private fun onWindowMinimized(RootNode: Node, minimized: Boolean) {
        if (minimized) {
            RootNode.stop()
        } else {
            RootNode.start()
        }
    }

    private fun getDeepLinkPath(): Path {
        return Path("AdaptableWindow")
            .appendSubPath("Drawer")
            .appendSubPath("Orders")
            .appendSubPath("Past")
            .appendSubPath("Page3")
    }

}