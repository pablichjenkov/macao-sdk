package com.pablichj.incubator.uistate3

import androidx.compose.ui.main.defaultUIKitMain
import androidx.compose.ui.window.Application

fun main() {
    defaultUIKitMain("Chat", Application("Chat") {
        ComposeApp()
    })
}
