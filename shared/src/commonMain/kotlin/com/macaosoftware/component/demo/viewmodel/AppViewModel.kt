package com.macaosoftware.component.demo.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.demo.viewmodel.factory.BottomNavigationDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.Demo3PageTopBarViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.DrawerComponentViewModelEmptyFactory
import com.macaosoftware.component.drawer.DrawerComponent
import com.macaosoftware.component.drawer.DrawerComponentDefaults
import com.macaosoftware.component.drawer.DrawerHeaderDefaultState
import com.macaosoftware.component.drawer.DrawerStatePresenterDefault
import com.macaosoftware.component.drawer.DrawerStyle
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponent
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentDefaults
import com.macaosoftware.component.demo.startup.StartupView
import com.macaosoftware.component.demo.startup.StartupViewModel
import com.macaosoftware.component.demo.startup.StartupViewModelFactory
import com.macaosoftware.component.stack.StackComponent
import com.macaosoftware.component.stack.StackComponentViewModel
import com.macaosoftware.component.stack.StackStatePresenterDefault
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults
import com.macaosoftware.component.viewmodel.StateComponent
import kotlinx.coroutines.Dispatchers

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
                val drawerComponent = buildDrawerComponent(appComponent)
                appComponent.navigator.push(drawerComponent)
            }
        ),
        content = TopBarComponentDefaults.TopBarComponentView
    ).apply {
        deepLinkPathSegment = "Onboard"
    }

    private val splashComponent = StateComponent<StartupViewModel>(
        viewModelFactory = StartupViewModelFactory {
            appComponent.navigator.push(customTopBarComponent)
        },
        content = StartupView
    )

    override fun onAttach() {
        splashComponent.setParent(appComponent)
        customTopBarComponent.setParent(appComponent)
    }

    override fun onStart() {
        appComponent.navigator.push(splashComponent)
    }

    override fun onStackTopUpdate(topComponent: Component) {

    }

    override fun onCheckChildForNextUriFragment(deepLinkPathSegment: String): Component? {
        // do not participate in deep link navigation
        return null
    }

    override fun onStop() {

    }

    override fun onDetach() {

    }

    private fun buildDrawerComponent(parentComponent: Component): Component {

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
            viewModelFactory = DrawerComponentViewModelEmptyFactory(
                drawerStatePresenter = DrawerStatePresenterDefault(
                    dispatcher = Dispatchers.Main,
                    drawerStyle = DrawerStyle(),
                    drawerHeaderState = DrawerHeaderDefaultState(
                        title = "Component Toolkit",
                        description = "I am a Drawer Component",
                        imageUri = "no_image",
                        style = DrawerStyle()
                    )
                )
            ),
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
            viewModelFactory = DrawerComponentViewModelEmptyFactory(
                drawerStatePresenter = DrawerStatePresenterDefault(
                    dispatcher = Dispatchers.Main,
                    drawerStyle = DrawerStyle(),
                    drawerHeaderState = DrawerHeaderDefaultState(
                        title = "Component Toolkit",
                        description = "I am a Drawer Component",
                        imageUri = "no_image",
                        style = DrawerStyle()
                    )
                )
            ),
            content = DrawerComponentDefaults.DrawerComponentView
        )

        return drawerComponent.also { it.setNavItems(drawerNavItems, 0) }
    }

}
