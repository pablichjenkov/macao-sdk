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
import com.pablichj.templato.component.demo.createCustomTopBarComponent

object AdaptableSizeTreeBuilder {

    private lateinit var adaptiveSizeComponent: AdaptiveSizeComponent
    private lateinit var subTreeNavItems: MutableList<NavItem>

    fun build(): AdaptiveSizeComponent {
        if (AdaptableSizeTreeBuilder::adaptiveSizeComponent.isInitialized) {
            return adaptiveSizeComponent
        }
        return AdaptiveSizeComponent().also {
            adaptiveSizeComponent = it
            it.uriFragment = "_navigator_adaptive"
        }
    }

    fun getOrCreateDetachedNavItems(): MutableList<NavItem> {

        if (AdaptableSizeTreeBuilder::subTreeNavItems.isInitialized) {
            return subTreeNavItems
        }

        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponent.createDefaultNavBarStatePresenter(),
            content = NavBarComponent.DefaultNavBarComponentView
        ).apply {
            uriFragment = "Orders"
        }

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = createCustomTopBarComponent("Orders/Current", {}).apply {
                    uriFragment = "Current"
                }
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = createCustomTopBarComponent("Orders/Past", {}).apply {
                    uriFragment = "Past"
                }
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = createCustomTopBarComponent("Orders/Claim", {}).apply {
                    uriFragment = "Claim"
                }
            )
        )

        navBarComponent.setNavItems(navbarNavItems, 0)

        val homeComponent = createCustomTopBarComponent(//HomeTopBarComponent(
            "Home",
            {},
        ).apply {
            uriFragment = "Home"
        }

        val settingsComponent = createCustomTopBarComponent(//SettingsTopBarComponent(
            "Settings",
            {},
        ).apply {
            uriFragment = "Settings"
        }

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