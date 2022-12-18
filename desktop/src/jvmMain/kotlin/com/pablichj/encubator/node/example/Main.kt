package com.pablichj.encubator.node.example

import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application
import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.JvmBackPressDispatcher

fun main() = application {

    val DesktopAppNode: DesktopAppNode = //remember(key1 = this) {
        DesktopAppStateTreeBuilder.getOrCreateDesktopAppNode(
            JvmBackPressDispatcher(),
            backPressedCallback = object : BackPressedCallback() {
                override fun onBackPressed() {}
            }
        )
    //}

    DesktopAppNode.Content(Modifier)
}

