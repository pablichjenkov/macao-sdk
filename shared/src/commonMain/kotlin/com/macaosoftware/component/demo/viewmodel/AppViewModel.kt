package com.macaosoftware.component.demo.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.push
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.demo.SplashComponent
import com.macaosoftware.component.demo.viewmodel.factory.BottomNavigationDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.Demo3PageTopBarViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.DrawerComponentViewModelEmptyFactory
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.navbar.BottomNavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationComponentDefaults
import com.macaosoftware.component.split.SplitComponent
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentViewModel
import com.macaosoftware.component.stack.StackStatePresenterDefault
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults

class AppViewModel(
    stackComponent: StackComponent<StackComponentViewModel>,
    override val stackStatePresenter: StackStatePresenterDefault
) : StackComponentViewModel(stackComponent) {

    private val appComponent = stackComponent

    private val customTopBarComponent: Component = TopBarComponent(
        viewModelFactory = Demo3PageTopBarViewModelFactory(
            topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
            screenName = "Onboard",
            onDone = {
                val drawerComponent = buildDrawerStateTree(appComponent)
                appComponent.push(drawerComponent)
            }
        ),
        content = TopBarComponentDefaults.TopBarComponentView
    ).apply {
        uriFragment = "Onboard"
    }

    private val splashComponent = SplashComponent {
        appComponent.push(customTopBarComponent)
    }

    override fun onCreate() {
        splashComponent.setParent(appComponent)
        customTopBarComponent.setParent(appComponent)
    }

    override fun onStart() {
        appComponent.push(splashComponent)
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
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Orders / Current",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        }

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Orders/Current",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Nested Node",
                icon = Icons.Filled.Email,
                component = splitComponent
            )
        )

        val bottomNavigationComponent = BottomNavigationComponent(
            viewModelFactory = BottomNavigationDemoViewModelFactory(
                BottomNavigationComponentDefaults.createBottomNavigationStatePresenter()
            ),
            content = BottomNavigationComponentDefaults.BottomNavigationComponentView
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Home",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Orders",
                icon = Icons.Filled.Edit,
                component = bottomNavigationComponent.also {
                    it.setNavItems(navbarNavItems, 0)
                },
            )
        )

        val drawerComponent = DrawerComponent(
            viewModelFactory = DrawerComponentViewModelEmptyFactory(),
            content = DrawerComponentDefaults.DrawerComponentView
        )

        return drawerComponent.apply {
            setParent(parentComponent)
            setNavItems(drawerNavItems, 0)
        }
    }

    private fun buildNestedDrawer(): DrawerComponent<DrawerComponentViewModelEmpty> {

        val navbarNavItems = mutableListOf(
            NavItem(
                label = "Current",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Orders/Current",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Orders/Past",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Orders/Claim",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        )

        val bottomNavigationComponent = BottomNavigationComponent(
            viewModelFactory = BottomNavigationDemoViewModelFactory(
                BottomNavigationComponentDefaults.createBottomNavigationStatePresenter()
            ),
            content = BottomNavigationComponentDefaults.BottomNavigationComponentView
        )

        val drawerNavItems = mutableListOf(
            NavItem(
                label = "Home Nested",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Home Nested",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Orders Nested",
                icon = Icons.Filled.Edit,
                component = bottomNavigationComponent.also { it.setNavItems(navbarNavItems, 0) },
            )
        )

        val drawerComponent = DrawerComponent(
            viewModelFactory = DrawerComponentViewModelEmptyFactory(),
            content = DrawerComponentDefaults.DrawerComponentView
        )

        return drawerComponent.also { it.setNavItems(drawerNavItems, 0) }
    }

}
