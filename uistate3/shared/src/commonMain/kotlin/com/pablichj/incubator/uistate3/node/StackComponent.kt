package com.pablichj.incubator.uistate3.node

import com.pablichj.incubator.uistate3.node.backstack.BackStack

interface StackComponent: ParentComponent {
    val backStack: BackStack<Component>
}