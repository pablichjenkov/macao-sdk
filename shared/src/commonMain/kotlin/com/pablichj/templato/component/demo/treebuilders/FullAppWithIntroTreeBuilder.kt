package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.pablichj.templato.component.demo.AppCoordinatorComponent
import com.pablichj.templato.component.demo.CustomTopBarComponent
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.drawer.DrawerComponent
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.split.SplitComponent
import com.pablichj.templato.component.core.stack.StackComponent
import com.pablichj.templato.component.platform.DiContainer
import com.pablichj.templato.component.platform.DispatchersProxy

object FullAppWithIntroTreeBuilder {
    private val diContainer = DiContainer(DispatchersProxy.DefaultDispatchers)
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
            navigationDrawerState = DrawerComponent.createDefaultState(),
            config = DrawerComponent.DefaultConfig,
            diContainer = diContainer
        )
        val NavBarNode = NavBarComponent()

        val SplitNavNode = SplitComponent(SplitComponent.DefaultConfig).apply {
            setTopComponent(buildNestedDrawer())
            setBottomComponent(
                CustomTopBarComponent(
                    "Orders / Current",
                    StackComponent.DefaultConfig
                ) {}
            )
        }

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Orders / Current",
                    StackComponent.DefaultConfig
                ) {},
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
                    StackComponent.DefaultConfig
                ) {},
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                component = NavBarNode.also {
                    it.setNavItems(navbarNavItems, 0)
                },
            )
        )

        return drawerComponent.apply {
            setParent(parentComponent)
            setNavItems(drawerNavItems, 0)
        }
    }

    private fun buildNestedDrawer(): DrawerComponent {
        val drawerComponent = DrawerComponent(
            navigationDrawerState = DrawerComponent.createDefaultState(),
            config = DrawerComponent.DefaultConfig,
            diContainer = diContainer
        )
        val NavBarNode = NavBarComponent()

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Orders / Current",
                    StackComponent.DefaultConfig
                ) {},
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = CustomTopBarComponent(
                    "Orders / Past",
                    StackComponent.DefaultConfig
                ) {},
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent(
                    "Orders / Claim",
                    StackComponent.DefaultConfig
                ) {},
            )
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home Nested",
                icon = Icons.Filled.Home,
                component = CustomTopBarComponent(
                    "Home",
                    StackComponent.DefaultConfig
                ) {},
            ),
            NavItem(
                label = "Orders Nested",
                icon = Icons.Filled.Edit,
                component = NavBarNode.also { it.setNavItems(navbarNavItems, 0) },
            )
        )

        return drawerComponent.also { it.setNavItems(drawerNavItems, 0) }
    }

}