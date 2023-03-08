package com.pablichj.incubator.uistate3.example.hotelBooking

import com.pablichj.incubator.uistate3.IosComponentRender
import com.pablichj.incubator.uistate3.node.Component
import platform.UIKit.UIViewController

fun ComponentRenderer(
    rootComponent: Component,
    appName: String,
): UIViewController = IosComponentRender(
    rootComponent,
    appName
)


fun buildAppComponent() : Component {
    return AppBuilder.buildGraph()
}

