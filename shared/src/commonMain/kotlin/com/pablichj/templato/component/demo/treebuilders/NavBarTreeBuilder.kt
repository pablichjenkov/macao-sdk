package com.pablichj.templato.component.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import com.pablichj.templato.component.core.NavItem
import com.pablichj.templato.component.core.navbar.NavBarComponent
import com.pablichj.templato.component.core.navbar.NavBarStateDefault
import com.pablichj.templato.component.core.navbar.NavBarStyle
import com.pablichj.templato.component.core.setNavItems
import com.pablichj.templato.component.core.stack.FixSizedPushStrategy
import com.pablichj.templato.component.core.topbar.TopBarComponent
import com.pablichj.templato.component.demo.CustomTopBarComponent
import com.pablichj.templato.component.platform.DiContainer
import com.pablichj.templato.component.platform.DispatchersProxy

object NavBarTreeBuilder {

    private lateinit var navBarComponent: NavBarComponent<NavBarStateDefault>

    fun build(): NavBarComponent<NavBarStateDefault> {

        if (NavBarTreeBuilder::navBarComponent.isInitialized) {
            return navBarComponent
        }

        val navbarNavItems = mutableListOf(
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
                icon = Icons.Filled.Settings,
                component = CustomTopBarComponent(
                    "Orders",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Add,
                component = CustomTopBarComponent(
                    "Settings",
                    TopBarComponent.DefaultConfig,
                    {},
                )
            )
        )

        return NavBarComponent(
            navBarState = NavBarComponent.createDefaultState(),
            config = NavBarComponent.DefaultConfig,
            content = NavBarComponent.DefaultNavBarComponentView
        ).also {
            it.setNavItems(navbarNavItems, 0)
            navBarComponent = it
            /*it.setNavBarComponentView { modifier: Modifier, childComponent: Component, navBarState: NavBarState ->
                Box(Modifier.fillMaxSize()) {
                    childComponent.Content(Modifier)
                    Text(modifier = Modifier.align(Alignment.Center),
                        text = "You should provide a Composable that encloses the render of childComponent.Content(Modifier)",
                        color = Color.Black
                    )
                }
            }*/
        }
    }

    private fun createNavBarConfig(): NavBarComponent.Config {
        return NavBarComponent.Config(
            pushStrategy = FixSizedPushStrategy(1),
            navBarStyle = NavBarStyle(),
            diContainer = DiContainer(DispatchersProxy.DefaultDispatchers)
        )
    }

}
