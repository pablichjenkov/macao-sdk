package com.pablichj.encubator.node.example

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application
import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.JvmBackPressDispatcher
import com.pablichj.encubator.node.example.statetrees.DesktopAppTreeBuilder

fun main() = application {

    val DesktopAppNode: DesktopAppNode = remember(key1 = this) {
        DesktopAppTreeBuilder.build()
    }

    DesktopAppNode.Content(Modifier)
}

