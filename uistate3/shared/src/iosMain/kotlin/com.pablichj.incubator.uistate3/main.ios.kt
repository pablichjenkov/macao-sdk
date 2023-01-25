package com.pablichj.incubator.uistate3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.Node
import platform.UIKit.UIViewController

fun IosNodeRender(
    rootNode: Node
): UIViewController = Application("State3 Demo") {

    val backPressDispatcher = remember {
        DefaultBackPressDispatcher()
    }

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides backPressDispatcher,
    ) {
        Box(modifier = Modifier.padding(top = 45.dp).fillMaxSize()) {
            val rootState = remember {
                RootState().also {
                    it.setRootNode(rootNode)
                    it.setBackPressHandler({ println("back pressed dispatched in root node") })
                }
            }

            rootState.PresentContent()

            LaunchedEffect(rootState) {
                rootState.start()
            }
            FloatingButton(
                modifier = Modifier.offset(y = 48.dp),
                alignment = Alignment.TopStart,
                onClick = { backPressDispatcher.dispatchBackPressed() }
            )
        }
    }

}

fun buildDrawerNode(): Node {
    return DrawerTreeBuilder.build()
}