package com.pablichj.incubator.uistate3.example.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.pablichj.incubator.uistate3.DrawerTreeBuilder
import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.adaptable.IWindowSizeInfoProvider
import com.pablichj.incubator.uistate3.node.adaptable.WindowSizeInfo
import platform.UIKit.UIViewController

fun ComponentRenderer(
    rootComponent: Component,
    appName: String
): UIViewController = com.pablichj.incubator.uistate3.IosComponentRender(
    rootComponent,
    appName
)

fun buildDrawerComponent() : Component{
    return DrawerTreeBuilder.build()
}

fun buildAdaptableSizeComponent(
    iosWindowSizeInfoDispatcher: IosWindowSizeInfoDispatcher
) : Component {
    return AdaptableSizeTreeBuilder.build(
        EmptyWindowSizeInfoProvider(IosWindowSizeInfoDispatcher())
    )
}

class EmptyWindowSizeInfoProvider(
    private val iosWindowSizeInfoDispatcher: IosWindowSizeInfoDispatcher
) : IWindowSizeInfoProvider(),  WindowSizeIndoObserver {

    private val windowSizeState = mutableStateOf<WindowSizeInfo>(WindowSizeInfo.Compact)

    init {
        iosWindowSizeInfoDispatcher.subscribe(this)
    }

    @Composable
    internal fun windowSizeInfo(): State<WindowSizeInfo> {
        return windowSizeState
    }

    override fun onWindowSizeChange() {
        windowSizeState.value = WindowSizeInfo.Compact
    }
}

class IosWindowSizeInfoDispatcher {
    fun subscribe(observer: WindowSizeIndoObserver) {}
}

interface WindowSizeIndoObserver {
    fun onWindowSizeChange()
}

