package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarStatePresenterDefault
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.demo.createCustomTopBarComponent

object NavBarTreeBuilder {

    private lateinit var navBarComponent: NavBarComponent<NavBarStatePresenterDefault>

    fun build(): NavBarComponent<NavBarStatePresenterDefault> {

        if (NavBarTreeBuilder::navBarComponent.isInitialized) {
            return navBarComponent
        }

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = createCustomTopBarComponent(
                    "Home",
                    {},
                )
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Settings,
                component = createCustomTopBarComponent(
                    "Orders",
                    {},
                )
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                component = createCustomTopBarComponent(
                    "Settings",
                    {},
                )
            )
        )

        return NavBarComponent(
            navBarStatePresenter = NavBarComponent.createDefaultNavBarStatePresenter(),
            // pushStrategy = FixSizedPushStrategy(1), // Uncomment to test other push strategies
            content = NavBarComponent.DefaultNavBarComponentView
        ).also {
            it.setNavItems(navbarNavItems, 0)
            navBarComponent = it
        }
    }

}
