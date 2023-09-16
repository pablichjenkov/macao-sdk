package com.macaosoftware.component.demo.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.demo.SplashComponent
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.split.SplitComponent
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentViewModel
import com.macaosoftware.component.stack.StackStatePresenterDefault
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults

class AppViewModel : StackComponentViewModel<StackStatePresenterDefault>() {

    lateinit var stackComponent: StackComponent<StackStatePresenterDefault>

    private val customTopBarComponent: Component = TopBarComponent(
        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
        componentViewModel = Demo3PageTopBarViewModel.create(
            screenName = "Onboard",
            onDone = {
                val drawerComponent = buildDrawerStateTree(stackComponent)
                stackComponent.backStack.push(drawerComponent)
            }
        ),
        content = TopBarComponentDefaults.TopBarComponentView
    ).apply {
        uriFragment = "Onboard"
    }

    private val splashComponent = SplashComponent {
        stackComponent.backStack.push(customTopBarComponent)
    }

    override fun onCreate(stackComponent: StackComponent<StackStatePresenterDefault>) {
        this.stackComponent = stackComponent
        splashComponent.setParent(stackComponent)
        customTopBarComponent.setParent(stackComponent)
    }

    override fun onStart() {
        stackComponent.backStack.push(splashComponent)
    }

    override fun onStackTopUpdate(topComponent: Component) {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }

    private fun buildDrawerStateTree(parentComponent: Component): Component {

        val splitComponent = SplitComponent(SplitComponent.DefaultConfig).apply {
            setTopComponent(buildNestedDrawer())
            setBottomComponent(
                TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Orders / Current", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        }

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Orders/Current", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Nested Node",
                icon = Icons.Filled.Email,
                component = splitComponent
            )
        )

        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentViewModel = NavBarComponentDefaults.createComponentViewModel(),
            content = NavBarComponentDefaults.NavBarComponentView
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Home", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
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

        val drawerComponent = DrawerComponent(
            drawerStatePresenter = DrawerComponentDefaults.createDrawerStatePresenter(),
            componentViewModel = DrawerComponentDefaults.createComponentViewModel(),
            content = DrawerComponentDefaults.DrawerComponentView
        )

        return drawerComponent.apply {
            setParent(parentComponent)
            setNavItems(drawerNavItems, 0)
        }
    }

    private fun buildNestedDrawer(): DrawerComponent<DrawerStatePresenterDefault> {

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Orders/Current", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Orders/Past", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Orders/Claim", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        )

        val navBarComponent = NavBarComponent(
            navBarStatePresenter = NavBarComponentDefaults.createNavBarStatePresenter(),
            componentViewModel = NavBarComponentDefaults.createComponentViewModel(),
            content = NavBarComponentDefaults.NavBarComponentView
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home Nested",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                    componentViewModel = Demo3PageTopBarViewModel.create("Home Nested", {}),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Orders Nested",
                icon = Icons.Filled.Edit,
                component = navBarComponent.also { it.setNavItems(navbarNavItems, 0) },
            )
        )

        val drawerComponent = DrawerComponent(
            drawerStatePresenter = DrawerComponentDefaults.createDrawerStatePresenter(),
            componentViewModel = DrawerComponentDefaults.createComponentViewModel(),
            content = DrawerComponentDefaults.DrawerComponentView
        )

        return drawerComponent.also { it.setNavItems(drawerNavItems, 0) }
    }

}
