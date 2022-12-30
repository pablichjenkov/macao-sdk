package com.pablichj.incubator.uistate3.example

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application
import com.pablichj.incubator.uistate3.example.treebuilders.DesktopAppTreeBuilder

fun main() = application {

    val DesktopAppNode: DesktopAppNode = remember(key1 = this) {
        DesktopAppTreeBuilder.build()
    }

    DesktopAppNode.Content(Modifier)
}

