package com.pablichj.incubator.uistate3.example.helloWorld

import com.pablichj.incubator.uistate3.IosBridge
import com.pablichj.incubator.uistate3.IosComponentRender
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.platform.DefaultAppLifecycleDispatcher
import com.pablichj.incubator.uistate3.platform.SafeAreaInsets
import platform.UIKit.UIViewController

fun ComponentRenderer(
    rootComponent: Component,
    iosBridge: IosBridge,
): UIViewController = IosComponentRender(
    rootComponent,
    iosBridge
)

fun buildHelloWorldComponent(): Component {
    return HelloWorldComponent()
}

fun createPlatformBridge(): IosBridge {
    return IosBridge(
        appLifecycleDispatcher = DefaultAppLifecycleDispatcher(),
        safeAreaInsets = SafeAreaInsets()
    )
}