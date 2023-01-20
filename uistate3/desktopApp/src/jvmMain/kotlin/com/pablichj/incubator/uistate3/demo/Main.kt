package com.pablichj.incubator.uistate3.demo

import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
import com.pablichj.incubator.uistate3.DesktopComposeApp
import com.pablichj.incubator.uistate3.demo.treebuilders.DesktopAppTreeBuilder

fun main() = application {

    val DesktopAppNode: DesktopAppNode = remember(key1 = this) {
        DesktopAppTreeBuilder.build()
    }

    DesktopComposeApp(DesktopAppNode)
}

