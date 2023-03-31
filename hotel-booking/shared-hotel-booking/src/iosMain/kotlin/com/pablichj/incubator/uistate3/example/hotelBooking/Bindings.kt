package com.pablichj.incubator.uistate3.example.hotelBooking

import com.pablichj.incubator.uistate3.IosBridge
import com.pablichj.incubator.uistate3.IosComponentRender
import com.pablichj.incubator.uistate3.node.Component
import platform.UIKit.UIViewController

fun ComponentRenderer(
    rootComponent: Component,
    iosBridge: IosBridge,
): UIViewController = IosComponentRender(
    rootComponent,
    iosBridge
)


fun buildAppComponent() : Component {
    return AppBuilder.buildGraph()
}

