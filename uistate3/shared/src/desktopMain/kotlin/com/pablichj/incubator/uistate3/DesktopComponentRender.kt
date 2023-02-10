package com.pablichj.incubator.uistate3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pablichj.incubator.uistate3.node.backstack.DefaultBackPressDispatcher
import com.pablichj.incubator.uistate3.node.backstack.ForwardBackPressCallback
import com.pablichj.incubator.uistate3.node.backstack.LocalBackPressedDispatcher
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.TreeContext
import com.pablichj.incubator.uistate3.node.dispatchAttachedToComponentTree

@Composable
fun DesktopComponentRender(
    rootComponent: Component,
    onBackPressEvent: () -> Unit
) {
    val desktopBackPressDispatcher = remember(rootComponent) {
        DefaultBackPressDispatcher()
    }

    val treeContext = remember(rootComponent) {
        TreeContext()
    }

    CompositionLocalProvider(
        LocalBackPressedDispatcher provides desktopBackPressDispatcher
    ) {
        Box {
            rootComponent.Content(Modifier.fillMaxSize())
            FloatingButton(
                modifier = Modifier.offset(y = 48.dp),
                alignment = Alignment.TopStart,
                onClick = { desktopBackPressDispatcher.dispatchBackPressed() }
            )

        }
    }

    LaunchedEffect(key1 = rootComponent, key2 = onBackPressEvent) {
        rootComponent.rootBackPressedCallbackDelegate = ForwardBackPressCallback {
            onBackPressEvent()
        }
        // Traverse the whole tree passing the TreeContext living in the root node. Useful to
        // propagate the the Navigator for example. Where each Component interested in participating
        // in deep linking will subscribe its instance an a DeepLinkMatcher lambda function.
        println("DesktopComponentRender::dispatchAttachedToComponentTree")
        rootComponent.dispatchAttachedToComponentTree(treeContext)
        rootComponent.start()
    }
}