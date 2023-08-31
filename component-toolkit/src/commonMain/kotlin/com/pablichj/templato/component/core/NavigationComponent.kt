package com.pablichj.templato.component.core

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector

interface NavigationComponent : ComponentWithBackStack {
    var navItems: MutableList<NavItem>
    var selectedIndex: Int
    var activeComponent: MutableState<Component?>

    fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>)
    fun updateSelectedNavItem(newTop: Component)

    interface LifecycleHandler {
        fun NavigationComponent.navigationComponentLifecycleStart()
        fun NavigationComponent.navigationComponentLifecycleStop()
        fun NavigationComponent.navigationComponentLifecycleDestroy()
    }
}

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val component: Component
)
