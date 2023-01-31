package com.pablichj.incubator.uistate3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.Node
import platform.UIKit.UIViewController

fun IosNodeRender(
    rootNode: Node,
    appName: String
): UIViewController = Application(appName) {

    val backPressDispatcher = remember {
        DefaultBackPressDispatcher()
    }

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides backPressDispatcher,
    ) {
        Box(modifier = Modifier.padding(top = 45.dp).fillMaxSize()) {

            rootNode.Content(Modifier.fillMaxSize())

            /* todo: Implement it outside this class in swift code*/
            /*FloatingButton(
                modifier = Modifier.offset (y = 48.dp),
                alignment = Alignment.BottomEnd,
                onClick = { backPressDispatcher.dispatchBackPressed() }
            )*/
        }
    }

    LaunchedEffect(key1 = rootNode/*, key2 = onBackPressEvent*/) {
        rootNode.context.rootNodeBackPressedDelegate = ForwardBackPressCallback {
            //onBackPressEvent()
            println("back pressed dispatched in root node")
        }
        rootNode.start()
    }
}


fun buildDrawerNode(): Node {
    return DrawerTreeBuilder.build()
}
