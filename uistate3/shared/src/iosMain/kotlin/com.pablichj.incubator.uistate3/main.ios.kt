package com.pablichj.incubator.uistate3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import com.pablichj.incubator.uistate3.node.backstack.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.backstack.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.backstack.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.TreeContext
import com.pablichj.incubator.uistate3.node.dispatchAttachedToComponentTree
import platform.UIKit.UIViewController

fun IosComponentRender(
    rootComponent: Component,
    appName: String
): UIViewController = Application(appName) {

    val backPressDispatcher = remember {
        DefaultBackPressDispatcher()
    }

    val treeContext = remember(rootComponent) {
        TreeContext()
    }

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides backPressDispatcher,
    ) {
        Box(modifier = Modifier.padding(top = 45.dp).fillMaxSize()) {

            rootComponent.Content(Modifier.fillMaxSize())

            /* todo: Implement it outside this class in swift code*/
            /*FloatingButton(
                modifier = Modifier.offset (y = 48.dp),
                alignment = Alignment.BottomEnd,
                onClick = { backPressDispatcher.dispatchBackPressed() }
            )*/
        }
    }

    LaunchedEffect(key1 = rootComponent/*, key2 = onBackPressEvent*/) {
        rootComponent.rootBackPressedCallbackDelegate = ForwardBackPressCallback {
            //onBackPressEvent()
            println("back pressed dispatched in root node")
        }
        // Traverse the whole tree passing the TreeContext living in the root node. Useful to
        // propagate the the Navigator for example. Where each Component interested in participating
        // in deep linking will subscribe its instance an a DeepLinkMatcher lambda function.
        println("IosComponentRender::dispatchAttachedToComponentTree")
        rootComponent.dispatchAttachedToComponentTree(treeContext)
        rootComponent.start()
    }
}


fun buildDrawerNode(): Component {
    return DrawerTreeBuilder.build()
}
