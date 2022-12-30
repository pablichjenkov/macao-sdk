package com.pablichj.encubator.node

import androidx.compose.ui.graphics.vector.ImageVector

interface NavigatorNode {

    val stack: ArrayDeque<Node>

    fun getNode(): Node

    fun getSelectedNavItemIndex(): Int

    fun setNavItems(
        navItemsList: MutableList<NavigatorNodeItem>,
        startingIndex: Int
    )

    fun getNavItems(): MutableList<NavigatorNodeItem>

    fun addNavItem(navItem: NavigatorNodeItem, index: Int)

    fun removeNavItem(index: Int)

    fun clearNavItems()

    fun transferFrom(donorNavigatorNode: NavigatorNode) {
        val donorStackCopy = donorNavigatorNode.stack
        val lastNodeToCopyIdx = donorStackCopy.size - 1
        stack.clear()
        for (idx in 0 .. lastNodeToCopyIdx) {
            stack.add(idx, donorStackCopy[idx])
        }

        setNavItems(
            navItemsList = donorNavigatorNode.getNavItems(),
            startingIndex = donorNavigatorNode.getSelectedNavItemIndex()
        )
    }

}

data class NavigatorNodeItem(
    val label: String,
    val icon: ImageVector,
    val node: Node,
    var selected: Boolean
)