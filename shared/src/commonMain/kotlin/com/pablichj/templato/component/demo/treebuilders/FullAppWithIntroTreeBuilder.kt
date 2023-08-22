package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.drawer.DrawerStatePresenterDefault
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.split.SplitComponent
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.demo.AppCoordinatorComponent
import com.pablichj.templato.component.demo.CustomTopBarComponent
import com.pablichj.templato.component.platform.DiContainer
import com.pablichj.templato.component.platform.CoroutineDispatchers

object FullAppWithIntroTreeBuilder {
    private val diContainer = DiContainer(CoroutineDispatchers.Defaults)
    private lateinit var appCoordinatorComponent: Component

    fun build(): Component {

        if (FullAppWithIntroTreeBuilder::appCoordinatorComponent.isInitialized) {
            return appCoordinatorComponent
        }

        return AppCoordinatorComponent().also {
            it.homeComponent = buildDrawerStateTree(it)
            appCoordinatorComponent = it
        }
    }

    private fun buildDrawerStateTree(parentComponent: Component): Component {
        val drawerComponent = DrawerComponent(
            drawerStatePresenter = DrawerComponent.createDefaultDrawerStatePresenter(),
            config = DrawerComponent.DefaultConfig,
            dispatchers = CoroutineDispatchers.Defaults,
            content = DrawerComponent.DefaultDrawerComponentView
        )
        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponent.createDefaultNavBarStatePresenter(),
            content = NavBarComponent.DefaultNavBarComponentView
        )

        val SplitNavNode = SplitComponent(SplitComponent.DefaultConfig).apply {
            setTopComponent(buildNestedDrawer())
            setBottomComponent(
                CustomTopBarComponent(
                    "Orders / Current",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            )
        }

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Orders / Current",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            ),
            NavItem(
                label = "Nested Node",
                icon = Icons.Filled.Email,
                component = SplitNavNode
            )
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Home",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                component = navBarComponent.also {
                    it.setNavItems(navbarNavItems, 0)
                },
            )
        )

        return drawerComponent.apply {
            setParent(parentComponent)
            setNavItems(drawerNavItems, 0)
        }
    }

    private fun buildNestedDrawer(): DrawerComponent<DrawerStatePresenterDefault> {
        val drawerComponent = DrawerComponent(
            drawerStatePresenter = DrawerComponent.createDefaultDrawerStatePresenter(),
            config = DrawerComponent.DefaultConfig,
            dispatchers = CoroutineDispatchers.Defaults,
            content = DrawerComponent.DefaultDrawerComponentView
        )
        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponent.createDefaultNavBarStatePresenter(),
            content = NavBarComponent.DefaultNavBarComponentView
        )

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Orders/Current",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = CustomTopBarComponent(
                    "Orders / Past",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent(
                    "Orders/Claim",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            )
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home Nested",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Home",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            ),
            NavItem(
                label = "Orders Nested",
                icon = Icons.Filled.Edit,
                component = navBarComponent.also { it.setNavItems(navbarNavItems, 0) },
            )
        )

        return drawerComponent.also { it.setNavItems(drawerNavItems, 0) }
    }

}