package com.pablichj.incubator.uistate3.node

import com.pablichj.incubator.uistate3.node.backstack.BackStack

fun NavComponent.setNavItems(
    newNavItems: MutableList<NavItem>,
    newSelectedIndex: Int
) {
    println("${getComponent().clazz}.setItems()")
    selectedIndex = newSelectedIndex

    newNavItems.map { nodeItem ->
        nodeItem.component.setParent(parentComponent = this@setNavItems.getComponent())
        nodeItem to nodeItem.component
    }.unzip().let {
        navItems = it.first.toMutableList()
        childComponents = it.second.toMutableList()
    }

    // Propagate TreeContext down to ensure all children share the same instance of TreeContext
    // this ParentComponent shares.
    with(getComponent()) {
        treeContext?.let { treeContext ->
            childComponents.forEach { it.dispatchAttachedToComponentTree(treeContext) }
        }
    }

    onSelectNavItem(selectedIndex, navItems)
}

internal fun NavComponent.addNavItem(newIndex: Int, navItem: NavItem) {
    if (newIndex < selectedIndex) {
        selectedIndex++
    }
    navItems.add(newIndex, navItem)
    childComponents.add(newIndex, navItem.component)

    // Let's update the UI
    onSelectNavItem(selectedIndex, navItems)
}


internal fun NavComponent.removeNavItem(removeIndex: Int) {
    if (removeIndex < selectedIndex) {
        selectedIndex--
    }
    navItems.removeAt(removeIndex)
    val removedComponent = childComponents.removeAt(removeIndex)
    onDestroyChildComponent(removedComponent)

    // Let's update the UI
    onSelectNavItem(selectedIndex, navItems)
}

internal fun NavComponent.clearNavItems() {
    println("${getComponent().clazz}.clearNavItems")
    backStack.clear()
    navItems.clear()
    selectedIndex = 0
    childComponents.clear()
    activeComponent.value = null
}

fun NavComponent.getNavItemFromNode(component: Component): NavItem {
    return navItems.first { it.component == component }
}

internal fun NavComponent.processBackstackEvent(event: BackStack.Event<Component>) {
    when (event) {
        is BackStack.Event.Push -> {
            println("${getComponent().clazz}::Event.Push")
            val stack = event.stack
            if (stack.size > 1) {
                val newTop = stack[stack.lastIndex]
                val oldTop = stack[stack.lastIndex - 1]
                transitionInOut(newTop, oldTop)
            } else {
                transitionIn(stack[0]) // There is only one item in the stack so lastIndex == 0
            }
        }
        is BackStack.Event.Pop -> {
            println("${getComponent().clazz}::Event.Pop")
            val stack = event.stack
            val oldTop = event.oldTop

            if (stack.isNotEmpty()) {
                val newTop = stack[stack.lastIndex]
                transitionInOut(newTop, oldTop)
            } else {
                transitionOut(oldTop) // There is no items in the stack, lets just
            }

        }
        is BackStack.Event.PushEqualTop -> {
            println(
                "${getComponent().clazz}::Event.PushEqualTop()," +
                        " backStack.size = ${backStack.size()}"
            )
        }
        is BackStack.Event.PopEmptyStack -> {
            println("${getComponent().clazz}::Event.PopEmptyStack(), backStack.size = 0")
            activeComponent.value = null
        }
    }
}

private fun NavComponent.transitionIn(newTop: Component) {
    println("${getComponent().clazz}::transitionIn(), newTop: ${newTop::class.simpleName}")
    newTop.start()
    updateSelectedNavItem(newTop)
    activeComponent.value = newTop
}

private fun NavComponent.transitionInOut(newTop: Component, oldTop: Component) {
    println(
        "${getComponent().clazz}::transitionInOut()," +
                " oldTop: ${oldTop::class.simpleName}, newTop: ${newTop::class.simpleName}"
    )

    // By convention always stop the previous top before starting the new one. TODO: Tests
    oldTop.stop()
    newTop.start()
    updateSelectedNavItem(newTop)
    activeComponent.value = newTop
}

private fun NavComponent.transitionOut(oldTop: Component) {
    println("${getComponent().clazz}::transitionOut(), oldTop: ${oldTop::class.simpleName}")
    oldTop.stop()
    activeComponent.value = null
}

internal fun NavComponent.transferFrom(donorNavComponent: NavComponent) {
    println("${getComponent().clazz}::transferFrom(...), donor stack.size = ${donorNavComponent.backStack.size()}")

    // Transfer backstack
    val donorStackCopy = donorNavComponent.backStack
    backStack.clear()
    for (idx in 0 until donorStackCopy.deque.size) {
        backStack.deque.add(idx, donorStackCopy.deque[idx])
    }

    // Transfer selectedIndex
    selectedIndex = donorNavComponent.selectedIndex

    // Transfer navItems and childComponents
    donorNavComponent.navItems.map { nodeItem ->
        nodeItem.component.setParent(parentComponent = this@transferFrom.getComponent())
        nodeItem to nodeItem.component
    }.unzip().let {
        navItems = it.first.toMutableList()
        childComponents = it.second.toMutableList()
    }

    // Transfer activeComponent
    activeComponent.value = donorNavComponent.activeComponent.value
    onSelectNavItem(selectedIndex, navItems)

    // Transfer Component properties
    this.getComponent().lifecycleState = donorNavComponent.getComponent().lifecycleState
    this.getComponent().treeContext = donorNavComponent.getComponent().treeContext

    // Make sure we don't keep references to the navItems in the donor Container
    donorNavComponent.clearNavItems()
}