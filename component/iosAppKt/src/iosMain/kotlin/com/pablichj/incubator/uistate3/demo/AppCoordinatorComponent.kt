package com.pablichj.incubator.uistate3.demo

import com.pablichj.incubator.uistate3.node.Component
import com.pablichj.incubator.uistate3.node.stack.StackBarItem
import com.pablichj.incubator.uistate3.node.stack.StackComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AppCoordinatorComponent : StackComponent() {

    private val splashComponent = SplashComponent {
        backStack.push(homeComponent)
    }.also { it.setParent(this@AppCoordinatorComponent) }

    //todo: Use setHomeNode instead, and attach to parent context, see SplitNode class as example
    lateinit var homeComponent: Component

    private val coroutineScope = CoroutineScope(Dispatchers.Main)// TODO: Use DispatchersBin

    override fun start() {
        super.start()
        println("IosAppCoordinatorComponent::start()")
        if (activeComponent.value == null) {
            backStack.push(splashComponent)
        } else {
            activeComponent.value?.start()
        }
    }

    override fun stop() {
        super.stop()
        println("IosAppCoordinatorComponent::stop()")
        activeComponent.value?.stop()
    }

    override fun getStackBarItemFromComponent(component: Component): StackBarItem? {
        return null
    }

}