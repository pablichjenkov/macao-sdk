package com.pablichj.encubator.node

import androidx.compose.ui.graphics.vector.ImageVector
import java.util.*

interface NavigatorNode {

    val stack: Stack<Node>

    fun getNode(): Node

    fun getSelectedNavItemIndex(): Int

    fun setNavItems(
        navItems: MutableList<NavigatorNodeItem>,
        startingIndex: Int
    )

    fun getNavItems(): MutableList<NavigatorNodeItem>

    fun addNavItem(navItem: NavigatorNodeItem, index: Int)

    fun removeNavItem(index: Int)

    fun clearNavItems()

    fun transferFrom(donorNavigatorNode: NavigatorNode) {
        val donorStackCopy = donorNavigatorNode.stack
        val lastNodeToCopyIdx = donorStackCopy.size
        stack.clear()
        for (idx in 0 until lastNodeToCopyIdx) { // last element exclusive
            stack.add(idx, donorStackCopy[idx])
        }

        setNavItems(
            navItems = donorNavigatorNode.getNavItems(),
            startingIndex = donorNavigatorNode.getSelectedNavItemIndex()
        )

        //donorNavigatorNode.clearNavItems()// release the old navigator references to the navItems
    }

}

data class NavigatorNodeItem(
    val label: String,
    val icon: ImageVector,
    val node: Node,
    var selected: Boolean
)