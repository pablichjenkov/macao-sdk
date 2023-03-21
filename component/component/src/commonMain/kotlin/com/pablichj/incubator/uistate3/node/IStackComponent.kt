package com.pablichj.incubator.uistate3.node

import com.pablichj.incubator.uistate3.node.backpress.BackStack

interface IStackComponent: IParentComponent {
    val backStack: BackStack<Component>
}