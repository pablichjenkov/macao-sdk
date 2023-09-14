package com.macaosoftware.component.core

import androidx.compose.ui.graphics.vector.ImageVector

interface NavigationComponent : ComponentWithBackStack {
    var navItems: MutableList<NavItem>
    var selectedIndex: Int

    fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>)
    fun updateSelectedNavItem(newTop: Component)

    interface LifecycleHandler {
        fun NavigationComponent.navigationComponentLifecycleStart()
        fun NavigationComponent.navigationComponentLifecycleStop()
        fun NavigationComponent.navigationComponentLifecycleDestroy()
    }
}

data class NavItem(
    val component: Component,
    val label: String,
    val icon: ImageVector,
    val badgeText: String? = null
)
