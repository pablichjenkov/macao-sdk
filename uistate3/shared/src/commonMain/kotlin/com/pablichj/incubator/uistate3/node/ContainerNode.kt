package com.pablichj.incubator.uistate3.node

import androidx.compose.ui.graphics.vector.ImageVector

interface ContainerNode {

    val stack: ArrayDeque<Node>

    fun getNode(): Node

    fun getSelectedItemIndex(): Int

    fun setItems(
        navItemsList: MutableList<NodeItem>,
        startingIndex: Int,
        isTransfer: Boolean = false
    )

    fun getItems(): MutableList<NodeItem>

    fun addItem(nodeItem: NodeItem, index: Int)

    fun removeItem(index: Int)

    fun clearItems()

    fun transferFrom(donorContainerNode: ContainerNode) {
        val donorStackCopy = donorContainerNode.stack
        stack.clear()
        for (idx in 0 until donorStackCopy.size) {
            stack.add(idx, donorStackCopy[idx])
        }

        setItems(
            navItemsList = donorContainerNode.getItems(),
            startingIndex = donorContainerNode.getSelectedItemIndex(),
            true
        )

        //donorContainerNode.clearItems()
    }

}

data class NodeItem(
    val label: String,
    val icon: ImageVector,
    val node: Node,
    var selected: Boolean
)