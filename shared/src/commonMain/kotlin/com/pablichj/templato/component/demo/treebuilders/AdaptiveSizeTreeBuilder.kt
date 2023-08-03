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
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.demo.CustomTopBarComponent
import com.pablichj.templato.component.demo.HomeTopBarComponent
import com.pablichj.templato.component.demo.SettingsTopBarComponent

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
            config = NavBarComponent.DefaultConfig,
            content = NavBarComponent.DefaultNavBarComponentView
        ).apply {
            uriFragment = "Orders"
        }

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Orders/Current",
                    TopBarComponent.DefaultConfig,
                    {},
                ).apply {
                    uriFragment = "Current"
                }
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = CustomTopBarComponent(
                    "Orders/Past",
                    TopBarComponent.DefaultConfig,
                    {},
                ).apply {
                    uriFragment = "Past"
                }
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent(
                    "Orders/Claim",
                    TopBarComponent.DefaultConfig,
                    {},
                ).apply {
                    uriFragment = "Claim"
                }
            )
        )

        navBarComponent.setNavItems(navbarNavItems, 0)

        val homeComponent = HomeTopBarComponent(
            "Home",
            TopBarComponent.DefaultConfig,
            {},
        ).apply {
            uriFragment = "Home"
        }

        val settingsComponent = SettingsTopBarComponent(
            "Settings",
            TopBarComponent.DefaultConfig,
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