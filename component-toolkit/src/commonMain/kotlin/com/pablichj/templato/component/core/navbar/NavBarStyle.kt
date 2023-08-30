package com.pablichj.templato.component.core.navbar

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavItem

data class NavBarStyle(
    val barSize: Dp = 56.dp,
    val showLabel: Boolean = true,
    val bgColor: Color = Color.Cyan,
    val unselectedColor: Color = Color.LightGray,
    val selectedColor: Color = Color.Gray,
    val textSize: Dp = 14.dp
)

data class NavBarNavItem(
    val label: String,
    val icon: ImageVector,
    var selected: Boolean,
    val component: Component
)

fun NavItem.toNavBarNavItem(selected: Boolean = false): NavBarNavItem {
    return NavBarNavItem(
        label = this.label,
        icon = this.icon,
        selected = selected,
        component = this.component
    )
}