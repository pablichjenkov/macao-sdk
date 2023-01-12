package com.pablichj.incubator.uistate3

import androidx.compose.ui.window.Application
import example.nodes.SplashNode
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController =
    Application("Chat") {
        SplashNode({})
    }

