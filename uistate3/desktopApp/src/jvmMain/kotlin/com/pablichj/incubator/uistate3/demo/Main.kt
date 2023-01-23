package com.pablichj.incubator.uistate3.demo

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
import com.pablichj.incubator.uistate3.DesktopNodeRender
import com.pablichj.incubator.uistate3.demo.treebuilders.DesktopAppTreeBuilder
import kotlin.system.exitProcess

fun main() = application {

    val DesktopAppNode: DesktopAppNode = remember(key1 = this) {
        DesktopAppTreeBuilder.build()
    }

    MaterialTheme {
        DesktopNodeRender(
            DesktopAppNode,
            onBackPressEvent = {
                exitProcess(0)
            }
        )
    }

}

