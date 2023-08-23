package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.pager.PagerComponent
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.demo.createCustomTopBarComponent

@OptIn(ExperimentalFoundationApi::class)
object PagerTreeBuilder {
    private lateinit var pagerComponent: PagerComponent

    fun build(): Component {

        if (this@PagerTreeBuilder::pagerComponent.isInitialized) {
            return pagerComponent
        }

        val navBarComponent1 = NavBarComponent(
            navBarStatePresenter = NavBarComponent.createDefaultNavBarStatePresenter(),
            content = NavBarComponent.DefaultNavBarComponentView
        )
        val navBarComponent2 = NavBarComponent(
            navBarStatePresenter = NavBarComponent.createDefaultNavBarStatePresenter(),
            content = NavBarComponent.DefaultNavBarComponentView
        )

        val navbarNavItems1 = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = createCustomTopBarComponent(
                    "Orders/Current",
                    {},
                )
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = createCustomTopBarComponent(
                    "Orders/Past",
                    {},
                )
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = createCustomTopBarComponent(
                    "Orders/Claim",
                    {},
                )
            )
        )

        val navbarNavItems2 = mutableListOf(
            NavItem(
                label = "Account",
                icon = Icons.Filled.Home,
                component = createCustomTopBarComponent(
                    "Settings/Account",
                    {},
                )
            ),
            NavItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                component = createCustomTopBarComponent(
                    "Settings/Profile",
                    {},
                )
            ),
            NavItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                component = createCustomTopBarComponent(
                    "Settings/About Us",
                    {},
                )
            )
        )

        val pagerNavItems = mutableListOf(
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
                icon = Icons.Filled.Edit,
                component = navBarComponent1.also { it.setNavItems(navbarNavItems1, 0) },
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = navBarComponent2.also { it.setNavItems(navbarNavItems2, 0) },
            )
        )

        return PagerComponent(
            content = PagerComponent.DefaultPagerComponentView
        ).also {
            pagerComponent = it
            it.setNavItems(
                pagerNavItems, 0
            )
        }
    }

}