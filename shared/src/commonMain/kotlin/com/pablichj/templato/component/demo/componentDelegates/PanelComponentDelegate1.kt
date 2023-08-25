package com.pablichj.templato.component.demo.componentDelegates

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.panel.PanelComponentDelegate
import com.pablichj.templato.component.core.panel.PanelNavItem
import com.pablichj.templato.component.core.panel.PanelStatePresenterDefault

class PanelComponentDelegate1(
    private val navItems: List<NavItem>
) : PanelComponentDelegate<PanelStatePresenterDefault>() {

    override fun mapComponentToPanelNavItem(component: Component): PanelNavItem {
        val navItem = navItems.firstOrNull {
            it.component == component
        }

        return if (navItem != null) {
            PanelNavItem(
                label = navItem.label,
                icon = navItem.icon,
                selected = false
            )
        } else {
            PanelNavItem(
                "Null PanelItem",
                Icons.Outlined.Close,
                false
            )
        }
    }
}
