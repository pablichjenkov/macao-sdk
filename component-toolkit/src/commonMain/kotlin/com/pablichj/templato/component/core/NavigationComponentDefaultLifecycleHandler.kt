package com.pablichj.templato.component.core

class NavigationComponentDefaultLifecycleHandler : NavigationComponent.LifecycleHandler {
    override fun onStart(navigationComponent: NavigationComponent) {
        navigationComponent.onStartBehavior()
    }

    override fun onStop(navigationComponent: NavigationComponent) {
        navigationComponent.onStopBehavior()
    }

    override fun onDestroy(navigationComponent: NavigationComponent) {
        navigationComponent.onDestroyBehavior()
    }
}

private fun NavigationComponent.onStartBehavior() {
    if (activeComponent.value == null) {
        if (getComponent().startedFromDeepLink) {
            return
        }
        println("${getComponent().instanceId()}::onStart(). Pushing selectedIndex = $selectedIndex, children.size = ${childComponents.size}")
        if (childComponents.isNotEmpty()) {
            backStack.push(childComponents[selectedIndex])
        } else {
            println("${getComponent().instanceId()}::onStart() with childComponents empty")
        }
    } else {
        println("${getComponent().instanceId()}::onStart() with activeChild = ${activeComponent.value?.instanceId()}")
        activeComponent.value?.dispatchStart()
    }
}

private fun NavigationComponent.onStopBehavior() {
    println("${getComponent().instanceId()}::onStop()")
    activeComponent.value?.dispatchStop()
}

private fun NavigationComponent.onDestroyBehavior() {
    println("${getComponent().instanceId()}::onDestroy()")
}
