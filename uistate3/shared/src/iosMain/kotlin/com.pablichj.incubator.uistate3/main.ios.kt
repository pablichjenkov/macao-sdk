package com.pablichj.incubator.uistate3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import com.pablichj.incubator.uistate3.node.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.Node
import example.nodes.SimpleNode
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
        Box(modifier = Modifier.fillMaxSize()) {
            val composeAppState = remember {
                AppStateHolder.ComposeAppState.also {
                    it.setRootNode(rootNode)
                    it.setBackPressHandler({ println("back pressed dispatched in rot node") })
                }
            }

            composeAppState.PresentContent()

            LaunchedEffect(composeAppState) {
                composeAppState.start()
            }
            FloatingButton(
                modifier = Modifier.offset(y = 48.dp),
                alignment = Alignment.TopStart,
                onClick = { backPressDispatcher.dispatchBackPressed() }
            )
        }
    }

}