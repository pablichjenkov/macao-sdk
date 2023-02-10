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

internal fun NavComponent.processBackstackEvent(event: BackStack.Event<Component>) {
    when (event) {
        is BackStack.Event.Push -> {
            val stack = event.stack
            val newTop = stack[stack.lastIndex]
            val oldTop = stack.getOrNull(stack.lastIndex - 1)

            println(
                "${getComponent().clazz}::Event.StackPush()," +
                        " oldTop: ${oldTop?.let { it::class.simpleName }}," +
                        " newTop: ${newTop::class.simpleName}"
            )

            // By convention always stop the previous top before starting the new one. TODO: Tests
            oldTop?.stop()
            newTop.start()
            updateSelectedNavItem(newTop)
            activeComponent.value = newTop
        }
        is BackStack.Event.Pop -> {
            val stack = event.stack
            val newTop = stack.getOrNull(stack.lastIndex)
            val oldTop = event.oldTop

            println(
                "$${getComponent().clazz}::Event.StackPop(), " +
                        "oldTop: ${oldTop::class.simpleName}," +
                        " newTop: ${newTop?.let { it::class.simpleName }}"
            )

            // By convention always stop the previous top before starting the new one. TODO: Tests
            oldTop.stop()
            newTop?.start()
            newTop?.let { updateSelectedNavItem(it) }
            activeComponent.value = newTop
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



