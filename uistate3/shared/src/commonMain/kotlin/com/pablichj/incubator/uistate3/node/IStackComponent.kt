package com.pablichj.incubator.uistate3.node

import com.pablichj.incubator.uistate3.node.backstack.BackStack

interface IStackComponent: IParentComponent {
    val backStack: BackStack<Component>
}