package com.macaosoftware.component.core

import com.macaosoftware.component.stack.BackStack
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.topbar.TopBarComponent

open class Navigator(val backStack: BackStack<Component>) {

    open fun push(component: Component) = backStack.push(component)

    open fun pop()  = backStack.pop()

    open fun popTo(
        component: Component,
        inclusive: Boolean
    )  = backStack.popTo(component, inclusive)

    open fun popToIndex(index: Int)  = backStack.popToIndex(index)

    open fun replaceTop(component: Component) = backStack.replaceTop(component)

    open fun top(): Component? = backStack.deque.lastOrNull()

    open fun canPop(): Boolean = backStack.size() > 1

    open fun stackSize() = backStack.size()

    open fun clearBackStack() = backStack.clear()
}

class StackComponentNavigator(
    val stackComponent: StackComponent<*>
) : Navigator(stackComponent.backStack) {

    override fun push(component: Component) {
        if(!stackComponent.childComponents.contains(component)) {
            stackComponent.childComponents.add(component)
        }
        super.push(component)
    }
}

class TopBarComponentNavigator(
    val topBarComponent: TopBarComponent<*>
) : Navigator(topBarComponent.backStack) {

    override fun push(component: Component) {
        if(!topBarComponent.childComponents.contains(component)) {
            topBarComponent.childComponents.add(component)
        }
        super.push(component)
    }
}
