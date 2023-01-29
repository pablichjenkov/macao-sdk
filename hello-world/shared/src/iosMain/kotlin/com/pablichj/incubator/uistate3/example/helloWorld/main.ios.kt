package com.pablichj.incubator.uistate3.example.helloWorld

import com.pablichj.incubator.uistate3.IosNodeRender
import com.pablichj.incubator.uistate3.node.Node
import platform.UIKit.UIViewController

fun ComposeAppViewController(
    rootNode: Node,
    appName: String
): UIViewController = IosNodeRender(
    rootNode,
    appName
)
