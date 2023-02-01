package com.pablichj.incubator.uistate3

import com.pablichj.incubator.uistate3.node.Node
import com.pablichj.incubator.uistate3.node.drawer.IDrawerNode

fun Node.findClosestIDrawerNode(): IDrawerNode? {
    var parentIterator: Node? = this.parentNode
    while (parentIterator != null) {
        if (parentIterator is IDrawerNode) {
            return parentIterator
        }
        parentIterator = parentIterator.parentNode
    }
    return null
}