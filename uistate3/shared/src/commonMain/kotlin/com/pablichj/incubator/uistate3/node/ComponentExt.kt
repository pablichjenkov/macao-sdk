package com.pablichj.incubator.uistate3.node

import com.pablichj.incubator.uistate3.node.drawer.IDrawerNode
import com.pablichj.incubator.uistate3.node.navigation.DeepLinkDestination

fun Component.findClosestIDrawerNode(): IDrawerNode? {
    var parentIterator: Component? = this.parentComponent
    while (parentIterator != null) {
        if (parentIterator is IDrawerNode) {
            return parentIterator
        }
        parentIterator = parentIterator.parentComponent
    }
    return null
}

fun Component.onAttachedToComponentTree(treeContext: TreeContext) {
    this.treeContext = treeContext

    // Register to handle deep links
    val deepLinkMatcherCopy = deepLinkMatcher
    if (deepLinkMatcherCopy != null) {
        treeContext.navigator.registerDestination(
            DeepLinkDestination(
                deepLinkMatcher = deepLinkMatcherCopy,
                component = this
            )
        )
    }
}

fun Component.dispatchAttachedToComponentTree(treeContext: TreeContext) {
    println("${clazz}::dispatchAttachedToComponentTree()")
    if (this is ParentComponent) {
        this.childComponents.forEach { it.dispatchAttachedToComponentTree(treeContext) }
    }
    this.onAttachedToComponentTree(treeContext)
}