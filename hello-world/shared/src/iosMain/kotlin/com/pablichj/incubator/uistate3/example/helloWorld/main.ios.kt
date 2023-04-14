package com.pablichj.incubator.uistate3.example.helloWorld

import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.IosComponentRender
import com.pablichj.templato.component.platform.DefaultAppLifecycleDispatcher
import com.pablichj.templato.component.platform.IosBridge
import com.pablichj.templato.component.platform.SafeAreaInsets
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