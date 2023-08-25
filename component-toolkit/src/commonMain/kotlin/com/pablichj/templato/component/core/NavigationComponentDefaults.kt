package com.pablichj.templato.component.core

object NavigationComponentDefaults {

    fun createLifecycleHandler(): NavigationComponentDefaultLifecycleHandler {
        return NavigationComponentDefaultLifecycleHandler()
    }

}

class NavigationComponentDefaultLifecycleHandler : NavigationComponent.LifecycleHandler {

    override fun NavigationComponent.navigationComponentLifecycleStart() {
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

    override fun NavigationComponent.navigationComponentLifecycleStop() {
        println("${getComponent().instanceId()}::onStop()")
        activeComponent.value?.dispatchStop()
    }

    override fun NavigationComponent.navigationComponentLifecycleDestroy() {
        println("${getComponent().instanceId()}::onDestroy()")
    }
}
