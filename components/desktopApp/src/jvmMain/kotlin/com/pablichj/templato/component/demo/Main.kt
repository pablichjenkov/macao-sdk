package com.pablichj.templato.component.demo

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application

fun main() = application {

    val DesktopAppNode: DesktopAppNode = remember(key1 = this) {
        DesktopAppNode()
    }

    MaterialTheme {
        DesktopAppNode.WindowContent(Modifier)
    }

}

