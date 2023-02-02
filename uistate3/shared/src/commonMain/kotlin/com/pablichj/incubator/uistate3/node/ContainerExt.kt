package com.pablichj.incubator.uistate3.node

import com.pablichj.incubator.uistate3.node.backstack.BackStack

fun Container.setItems(
    navItems: MutableList<NodeItem>,
    selectedIndex: Int,
    isTransfer: Boolean = false
) {
    this.selectedIndex = selectedIndex

    //navItems = navItemsList.map { it }.toMutableList()
    this.navItems = navItems

    var selectedComponentFromTransfer: Component? = null
    this.childComponents = navItems.mapIndexed { idx, navItem ->
        navItem.component.also {
            it.attachToParent(parentComponent = this.getComponent())
            if (idx == selectedIndex) {
                selectedComponentFromTransfer = it
            }
        }
    }.toMutableList()

    onSelectNavItem(selectedIndex, navItems)

    // If setItem() is called after start() was call, then we update the UI right here
    if (getComponent().lifecycleState != Component.LifecycleState.Started) {
        if (isTransfer) {
            activeComponent.value = selectedComponentFromTransfer
        }
    }
}

internal fun Container.addItem(newIndex: Int, nodeItem: NodeItem) {
    if (newIndex < selectedIndex) {
        selectedIndex++
    }
    navItems.add(newIndex, nodeItem)
    childComponents.add(newIndex, nodeItem.component)

    // Let's update the UI
    onSelectNavItem(selectedIndex, navItems)
}


internal fun Container.removeItem(removeIndex: Int) {
    if (removeIndex < selectedIndex) {
        selectedIndex--
    }
    navItems.removeAt(removeIndex)
    onDestroyChildComponent(childComponents.removeAt(removeIndex))

    // Let's update the UI
    onSelectNavItem(selectedIndex, navItems)
}

internal fun Container.clearItems() {
    println("Navbar.clearNavItems")
    navItems.clear()
    childComponents.clear()
    backStack.clear()
}

internal fun Container.processBackstackEvent(event: BackStack.Event<Component>) {
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

            newTop.start()
            oldTop?.stop()
            activeComponent.value = newTop
            updateSelectedNavItem(newTop)
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

            activeComponent.value = newTop
            newTop?.start()
            oldTop.stop()

            newTop?.let { updateSelectedNavItem(it) }
        }
        is BackStack.Event.PushEqualTop -> {
            println("${getComponent().clazz}::Event.PushEqualTop()," +
                    " backStack.size = ${backStack.size()}")
        }
        is BackStack.Event.PopEmptyStack -> {
            println("${getComponent().clazz}::Event.PopEmptyStack(), backStack.size = 0")
        }
    }
}

/*fun transferFrom(donorContainer: Container) {
        val donorStackCopy = donorContainer.backStack
        backStack.clear()
        for (idx in 0 until donorStackCopy.size) {
            backStack.add(idx, donorStackCopy[idx])
        }

        setItems(
            navItems = donorContainer.getItems(),
            selectedIndex = donorContainer.getSelectedItemIndex(),
            true
        )

        //donorContainerNode.clearItems()
    }*/
internal fun Container.transferFrom(donorContainer: Container) {
    val donorStackCopy = donorContainer.backStack
    backStack.clear()
    for (idx in 0 until donorStackCopy.deque.size) {
        backStack.deque.add(idx, donorStackCopy.deque[idx])
    }

    setItems(
        navItems = donorContainer.navItems,
        selectedIndex = donorContainer.selectedIndex,
        true
    )

    //donorContainerNode.clearItems()
}



