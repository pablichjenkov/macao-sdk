package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.lifecycle.ViewModel
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.adaptable.AdaptableSizeComponent
import com.pablichj.incubator.uistate3.node.adaptable.IWindowSizeInfoProvider
import com.pablichj.incubator.uistate3.node.drawer.DrawerComponent
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import example.nodes.TopBarComponent
import com.pablichj.incubator.uistate3.node.panel.PanelComponent
import com.pablichj.incubator.uistate3.node.setItems

class AdaptableSizeStateTreeHolder : ViewModel() {

    private lateinit var AdaptableSizeNode: AdaptableSizeComponent
    private lateinit var subTreeNavItems: MutableList<NavItem>

    fun getOrCreate(
        windowSizeInfoProvider: IWindowSizeInfoProvider,
    ): AdaptableSizeComponent {

        if (this::AdaptableSizeNode.isInitialized) {
            return AdaptableSizeNode.apply {
                this.windowSizeInfoProvider = windowSizeInfoProvider
            }
        }

        return AdaptableSizeComponent(
            windowSizeInfoProvider
        ).also {
            it.setNavItems(
                getOrCreateDetachedNavItems(), 0
            )
            it.setCompactContainer(DrawerComponent())
            it.setMediumContainer(NavBarComponent())
            it.setExpandedContainer(PanelComponent())
            AdaptableSizeNode = it
        }

    }

    fun getOrCreateDetachedNavItems(): MutableList<NavItem> {

        if (this::subTreeNavItems.isInitialized) {
            return subTreeNavItems
        }

        val NavBarNode = NavBarComponent()

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent("Orders / Current", Icons.Filled.Home) {},
                selected = false
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = TopBarComponent("Orders / Past", Icons.Filled.Edit) {},
                selected = false
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = TopBarComponent("Orders / Claim", Icons.Filled.Email) {},
                selected = false
            )
        )

        NavBarNode.setItems(navbarNavItems, 0)

        val navItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    "Home",
                    Icons.Filled.Home
                ) {},
                selected = false
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = NavBarNode,
                selected = false
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = TopBarComponent(
                    "Settings",
                    Icons.Filled.Email
                ) {},
                selected = false
            )
        )

        return navItems.also { subTreeNavItems = it }
    }

}