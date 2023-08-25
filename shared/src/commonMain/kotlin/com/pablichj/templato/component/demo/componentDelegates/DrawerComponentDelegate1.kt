package com.pablichj.templato.component.demo.componentDelegates

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.drawer.DrawerComponentDelegate
import com.pablichj.templato.component.core.drawer.DrawerNavItem
import com.pablichj.templato.component.core.drawer.DrawerStatePresenterDefault
import com.pablichj.templato.component.core.navbar.NavBarComponentDelegate
import com.pablichj.templato.component.core.navbar.NavBarNavItem
import com.pablichj.templato.component.core.navbar.NavBarStatePresenterDefault

class DrawerComponentDelegate1(
    private val navItems: List<NavItem>
) : DrawerComponentDelegate<DrawerStatePresenterDefault>() {

    override fun mapComponentToDrawerNavItem(component: Component): DrawerNavItem {
        val navItem = navItems.firstOrNull {
            it.component == component
        }

        return if (navItem != null) {
            DrawerNavItem(
                label = navItem.label,
                icon = navItem.icon,
                selected = false
            )
        } else {
            DrawerNavItem(
                "Null DrawerItem",
                Icons.Outlined.Close,
                false
            )
        }
    }
}
