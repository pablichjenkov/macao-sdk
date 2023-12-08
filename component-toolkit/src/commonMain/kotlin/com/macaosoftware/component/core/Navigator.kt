package com.macaosoftware.component.core

import com.macaosoftware.component.stack.BackStack

class Navigator(val backStack: BackStack<Component>)

fun Navigator.push(component: Component)  = backStack.push(component)

fun Navigator.pop()  = backStack.pop()

fun Navigator.popTo(
    component: Component,
    inclusive: Boolean
)  = backStack.popTo(component, inclusive)

fun Navigator.popToIndex(index: Int)  = backStack.popToIndex(index)

fun Navigator.replaceTop(component: Component) = backStack.replaceTop(component)

fun Navigator.top(): Component? = backStack.deque.lastOrNull()

fun Navigator.canPop(): Boolean = backStack.size() > 1

fun Navigator.stackSize() = backStack.size()

fun Navigator.clearBackStack() = backStack.clear()
