package com.pablichj.incubator.uistate3.node

import com.pablichj.incubator.uistate3.node.drawer.IDrawerNode

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