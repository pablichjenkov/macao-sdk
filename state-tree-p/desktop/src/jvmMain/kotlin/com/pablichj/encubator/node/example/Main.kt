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
import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.JvmBackPressDispatcher
import com.pablichj.encubator.node.JvmWindowSizeInfoProvider
import com.pablichj.encubator.node.Node
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
            setCompactNavigator(DrawerNode(context))
            setMediumNavigator(NavBarNode(context))
            setExpandedNavigator(PanelNode(context))
        }
    }

    Window(onCloseRequest = ::exitApplication, windowState) {
        MenuBar {
            Menu("File") {
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