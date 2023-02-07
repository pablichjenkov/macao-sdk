package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.adaptable.AdaptableSizeComponent
import com.pablichj.incubator.uistate3.node.adaptable.IWindowSizeInfoProvider
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.navigation.ComponentDestination
import com.pablichj.incubator.uistate3.node.navigation.Navigator
import com.pablichj.incubator.uistate3.node.navigation.SubPath
import com.pablichj.incubator.uistate3.node.setNavItems
import example.nodes.TopBarComponent

object AdaptableSizeTreeBuilder {

    private lateinit var AdaptableSizeNode: AdaptableSizeComponent
    private lateinit var subTreeNavItems: MutableList<NavItem>

    fun build(
        windowSizeInfoProvider: IWindowSizeInfoProvider
    ): AdaptableSizeComponent {

        if (AdaptableSizeTreeBuilder::AdaptableSizeNode.isInitialized) {
            return AdaptableSizeNode.apply {
                this.windowSizeInfoProvider = windowSizeInfoProvider
            }
        }

        return AdaptableSizeComponent(
            windowSizeInfoProvider
        ).also {
            it.subPath = SubPath("AdaptableWindow")
            AdaptableSizeNode = it
        }

    }

    fun getOrCreateDetachedNavItems(): MutableList<NavItem> {

        if (AdaptableSizeTreeBuilder::subTreeNavItems.isInitialized) {
            return subTreeNavItems
        }

        val NavBarComponent = NavBarComponent()
            .apply {
                subPath = SubPath("Orders")
                Navigator.registerDestination(
                    ComponentDestination("Orders/Page1", this)
                )
            }

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    "Orders / Current", Icons.Filled.Home, {}
                ).apply { subPath = SubPath("Current") },
                selected = false
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = TopBarComponent(
                    "Orders / Past", Icons.Filled.Edit, {}
                ).apply { subPath = SubPath("Past") },
                selected = false
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = TopBarComponent("Orders / Claim", Icons.Filled.Email, {})
                    .apply { subPath = SubPath("Claim") },
                selected = false
            )
        )

        NavBarComponent.setNavItems(navbarNavItems, 0)

        val SettingsComponent =
            TopBarComponent("Settings", Icons.Filled.Email, {})
                .apply {
                    subPath = SubPath("Settings")
                    Navigator.registerDestination(
                        ComponentDestination(
                            "Settings/Page1",
                            this
                        )
                    )
                }

        val HomeComponent =
            TopBarComponent(
                "Home", Icons.Filled.Home, {}
            ).apply {
                subPath = SubPath("Home")
                Navigator.registerDestination(
                    ComponentDestination(
                        "Home/Page1",
                        this
                    )
                )
            }

        val navItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = HomeComponent,
                selected = false
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = NavBarComponent,
                selected = false
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = SettingsComponent,
                selected = false
            )
        )

        return navItems.also { subTreeNavItems = it }
    }

}