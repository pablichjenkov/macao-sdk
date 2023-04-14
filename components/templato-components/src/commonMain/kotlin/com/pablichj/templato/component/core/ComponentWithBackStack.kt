package com.pablichj.templato.component.core

import com.pablichj.templato.component.core.stack.BackStack

interface ComponentWithBackStack: ComponentWithChildren {
    val backStack: BackStack<Component>
}