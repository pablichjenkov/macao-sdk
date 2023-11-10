package com.macaosoftware.component.bottomnavigation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavItem

data class BottomNavigationStyle(
    val barSize: Dp = 56.dp,
    val showLabel: Boolean = true,
    val bgColor: Color = Color.Cyan,
    val unselectedColor: Color = Color.LightGray,
    val selectedColor: Color = Color.Gray,
    val textSize: Dp = 14.dp
)

data class BottomNavigationNavItem(
    val label: String,
    val icon: ImageVector,
    var selected: Boolean,
    val component: Component
)

fun NavItem.toBottomNavigationNavItem(selected: Boolean = false): BottomNavigationNavItem {
    return BottomNavigationNavItem(
        label = this.label,
        icon = this.icon,
        selected = selected,
        component = this.component
    )
}
