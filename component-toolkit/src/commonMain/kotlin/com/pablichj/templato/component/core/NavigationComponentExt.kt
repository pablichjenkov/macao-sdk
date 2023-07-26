package com.pablichj.templato.component.core

import com.pablichj.templato.component.core.stack.StackTransition

fun NavigationComponent.setNavItems(
    newNavItems: MutableList<NavItem>,
    newSelectedIndex: Int
) {
    println("${getComponent().instanceId()}.setItems()")
    selectedIndex = newSelectedIndex

    newNavItems.map { navItem ->
        navItem.component.setParent(parentComponent = this@setNavItems.getComponent())
        navItem to navItem.component
    }.unzip().let {
        navItems = it.first.toMutableList()
        childComponents = it.second.toMutableList()
    }

    onSelectNavItem(selectedIndex, navItems)
}

internal fun NavigationComponent.addNavItem(newIndex: Int, navItem: NavItem) {
    if (newIndex < selectedIndex) {
        selectedIndex++
    }
    navItems.add(newIndex, navItem)
    childComponents.add(newIndex, navItem.component)

    // Let's update the UI
    onSelectNavItem(selectedIndex, navItems)
}


internal fun NavigationComponent.removeNavItem(removeIndex: Int) {
    if (removeIndex < selectedIndex) {
        selectedIndex--
    }
    navItems.removeAt(removeIndex)
    val removedComponent = childComponents.removeAt(removeIndex)
    onDestroyChildComponent(removedComponent)

    // Let's update the UI
    onSelectNavItem(selectedIndex, navItems)
}

internal fun NavigationComponent.clearNavItems() {
    println("${getComponent().instanceId()}.clearNavItems")
    backStack.clear()
    navItems.clear()
    selectedIndex = 0
    childComponents.clear()
    activeComponent.value = null
}

fun NavigationComponent.getNavItemFromComponent(component: Component): NavItem {
    return navItems.first { it.component == component }
}

internal fun NavigationComponent.processBackstackTransition(
    stackTransition: StackTransition<Component>
) {
    when (stackTransition) {
        is StackTransition.In -> {
            updateSelectedNavItem(stackTransition.newTop)
            activeComponent.value = stackTransition.newTop
        }
        is StackTransition.InOut -> {
            updateSelectedNavItem(stackTransition.newTop)
            activeComponent.value = stackTransition.newTop
        }
        is StackTransition.InvalidPushEqualTop -> {}
        is StackTransition.InvalidPopEmptyStack -> {
            activeComponent.value = null
        }
        is StackTransition.Out -> {
            activeComponent.value = null
        }
    }
}

internal fun NavigationComponent.transferFrom(donorNavComponent: NavigationComponent) {
    println("${getComponent().instanceId()}::transferFrom(...), donor stack.size = ${donorNavComponent.backStack.size()}")

    // Transfer backstack
    val donorStackCopy = donorNavComponent.backStack
    backStack.clear()
    for (idx in 0 until donorStackCopy.deque.size) {
        backStack.deque.add(idx, donorStackCopy.deque[idx])
    }

    // Transfer selectedIndex
    selectedIndex = donorNavComponent.selectedIndex

    // Transfer navItems and childComponents
    donorNavComponent.navItems.map { navItem ->
        navItem.component.setParent(parentComponent = this@transferFrom.getComponent())
        navItem to navItem.component
    }.unzip().let {
        navItems = it.first.toMutableList()
        childComponents = it.second.toMutableList()
    }

    // Transfer activeComponent
    activeComponent.value = donorNavComponent.activeComponent.value
    onSelectNavItem(selectedIndex, navItems)

    // Transfer Component properties
    this.getComponent().lifecycleState = donorNavComponent.getComponent().lifecycleState

    // Make sure we don't keep references to the navItems in the donor Container
    donorNavComponent.clearNavItems()
}
