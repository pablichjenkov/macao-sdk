package com.pablichj.incubator.uistate3.node

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector
import com.pablichj.incubator.uistate3.node.backstack.BackStack

interface INavComponent {
    val backStack: BackStack<Component>
    var navItems: MutableList<NodeItem>
    var selectedIndex: Int
    var childComponents: MutableList<Component>
    var activeComponent: MutableState<Component?>

    fun getComponent(): Component
    fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NodeItem>)
    fun updateSelectedNavItem(newTop: Component)
    fun onDestroyChildComponent(component: Component)
}

data class NodeItem(
    val label: String,
    val icon: ImageVector,
    val component: Component,
    var selected: Boolean
)