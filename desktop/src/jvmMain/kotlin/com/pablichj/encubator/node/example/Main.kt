package com.pablichj.encubator.node.example

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.pablichj.encubator.node.*
import com.pablichj.encubator.node.drawer.DrawerNode
import com.pablichj.encubator.node.navbar.NavBarNode
import com.pablichj.encubator.node.panel.PanelNode
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun main() = application {

    val windowState = rememberWindowState(
        size = DpSize(width = 400.dp, height = 800.dp)
    )

    val RootNode = remember<Node>(key1 = windowState) {
        val subtreeNavItems = AdaptableWindowTreeBuilder.getOrCreateDetachedNavItems()

        val AdaptableWindowNode = AdaptableWindowTreeBuilder.build(
            JvmWindowSizeInfoProvider(windowState),
            JvmBackPressDispatcher(),
            backPressedCallback = object : BackPressedCallback() {
                override fun onBackPressed() {}
            }
        )

        AdaptableWindowNode.apply {
            setNavItems(subtreeNavItems, 0)
            setCompactNavigator(DrawerNode(context).apply { context.subPath = SubPath("Drawer") })
            setMediumNavigator(NavBarNode(context).apply { context.subPath = SubPath("Navbar") })
            setExpandedNavigator(PanelNode(context).apply { context.subPath = SubPath("Panel") })
        }
    }

    Window(onCloseRequest = ::exitApplication, windowState) {
        MenuBar {
            Menu("File") {
                Item(
                    "Deep Link",
                    onClick = {
                        val path = getDeepLinkPath()
                        val deepLinkResult = RootNode.checkDeepLinkMatch(path)
                        println(deepLinkResult.toString())
                        if (deepLinkResult == DeepLinkResult.Success) {
                            //todo nice function to reuse the same path. See how replace it
                            path.moveToStart()
                            RootNode.navigateUpToDeepLink(path)
                        }
                    }
                )
                Item("New window", onClick = { /*state.openNewWindow*/ })
                Item("Exit", onClick = {/*state.exit*/ })
            }
        }
        RootNode.Content(Modifier)
        LaunchedEffect(windowState) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        onWindowMinimized(RootNode, it)
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
}