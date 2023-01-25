package com.pablichj.incubator.uistate3.demo

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application
import com.pablichj.incubator.uistate3.demo.treebuilders.DesktopAppTreeBuilder

fun main() = application {

    val DesktopAppNode: DesktopAppNode = remember {
        DesktopAppTreeBuilder.build()
    }

    MaterialTheme {
        DesktopAppNode.WindowContent(Modifier)
    }

}

