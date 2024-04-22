package com.macaosoftware.component.demo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application

fun main() = application {
    val multiWindowComponent = remember(key1 = this) {
        DesktopMultiWindowComponent()
    }
    MaterialTheme {
        multiWindowComponent.Content(Modifier)
    }
}
