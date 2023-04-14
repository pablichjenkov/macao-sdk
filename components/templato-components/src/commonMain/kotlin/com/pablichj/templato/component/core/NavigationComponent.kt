package com.pablichj.templato.component.core

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector

interface NavigationComponent : ComponentWithBackStack {
    var navItems: MutableList<NavItem>
    var selectedIndex: Int
    var activeComponent: MutableState<Component?>

    fun onSelectNavItem(selectedIndex: Int, navItems: MutableList<NavItem>)
    fun updateSelectedNavItem(newTop: Component)
}

data class NavItemDeco(
    val label: String,
    val icon: ImageVector,
    val component: Component,
    var selected: Boolean
)

fun NavItem.toNavItemDeco(selected: Boolean = false): NavItemDeco {
    return NavItemDeco(
        label = this.label,
        icon = this.icon,
        component = this.component,
        selected = selected
    )
}

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val component: Component
)