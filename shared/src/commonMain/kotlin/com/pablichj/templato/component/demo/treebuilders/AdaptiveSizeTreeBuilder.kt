package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.adaptive.AdaptiveSizeComponent
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.demo.CustomTopBarComponent
import com.pablichj.templato.component.core.stack.StackComponent

object AdaptableSizeTreeBuilder {

    private lateinit var adaptiveSizeComponent: AdaptiveSizeComponent
    private lateinit var subTreeNavItems: MutableList<NavItem>

    fun build(): AdaptiveSizeComponent {
        if (AdaptableSizeTreeBuilder::adaptiveSizeComponent.isInitialized) {
            return adaptiveSizeComponent
        }
        return AdaptiveSizeComponent().also {
            adaptiveSizeComponent = it
        }
    }

    fun getOrCreateDetachedNavItems(): MutableList<NavItem> {

        if (AdaptableSizeTreeBuilder::subTreeNavItems.isInitialized) {
            return subTreeNavItems
        }

        val navBarComponent = NavBarComponent()

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Orders",// / Current
                    StackComponent.DefaultConfig,
                    {}
                )
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = CustomTopBarComponent("Orders / Past", StackComponent.DefaultConfig, {})
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent(
                    "Orders / Claim",
                    StackComponent.DefaultConfig,
                    {}
                )
            )
        )

        navBarComponent.setNavItems(navbarNavItems, 0)

        val settingsComponent = CustomTopBarComponent("Settings", StackComponent.DefaultConfig, {})

        val homeComponent = CustomTopBarComponent("Home", StackComponent.DefaultConfig, {})

        val navItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = homeComponent,
            ), NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = navBarComponent,
            ), NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = settingsComponent,
            )
        )

        return navItems.also { subTreeNavItems = it }
    }

}