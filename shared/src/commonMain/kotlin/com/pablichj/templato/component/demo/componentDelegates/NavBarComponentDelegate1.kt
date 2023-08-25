package com.pablichj.templato.component.demo.componentDelegates

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.navbar.NavBarComponentDelegate
import com.pablichj.templato.component.core.navbar.NavBarNavItem
import com.pablichj.templato.component.core.navbar.NavBarStatePresenterDefault

class NavBarComponentDelegate1(
    private val navItems: List<NavItem>
) : NavBarComponentDelegate<NavBarStatePresenterDefault>() {

    override fun mapComponentToNavBarNavItem(component: Component): NavBarNavItem {
        val navItem = navItems.firstOrNull {
            it.component == component
        }

        return if (navItem != null) {
            NavBarNavItem(
                label = navItem.label,
                icon = navItem.icon,
                selected = false
            )
        } else {
            NavBarNavItem(
                "Null NavItem",
                Icons.Outlined.Close,
                false
            )
        }
    }
}
