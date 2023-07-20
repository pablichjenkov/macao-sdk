package com.pablichj.templato.component.demo

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application

fun main() = application {
    val desktopAppComponent = remember(key1 = this) {
        DesktopAppComponent()
    }
    MaterialTheme {
        desktopAppComponent.Content(Modifier)
    }
}
