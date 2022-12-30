package com.pablichj.incubator.uistate3

import androidx.compose.ui.window.Window
import platform.AppKit.NSApp
import platform.AppKit.NSApplication

fun main() {
    NSApplication.sharedApplication()
    Window("Chat App") {
        ComposeApp()
    }
    NSApp?.run()
}
