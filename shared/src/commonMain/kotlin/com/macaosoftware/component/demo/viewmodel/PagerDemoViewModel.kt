package com.macaosoftware.component.demo.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import com.macaosoftware.component.core.NavItem
import com.macaosoftware.component.core.setNavItems
import com.macaosoftware.component.demo.viewmodel.factory.BottomNavigationDemoViewModelFactory
import com.macaosoftware.component.demo.viewmodel.factory.Demo3PageTopBarViewModelFactory
import com.macaosoftware.component.navbar.NavBarComponent
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.pager.PagerComponent
import com.macaosoftware.component.pager.PagerComponentViewModel
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentDefaults

class PagerDemoViewModel(
    pagerComponent: PagerComponent
) : PagerComponentViewModel(pagerComponent) {

    override fun onCreate() {
        pagerComponent.setNavItems(
            navItems = createPagerItems(),
            selectedIndex = 0
        )
    }

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }

    private fun createPagerItems(): MutableList<NavItem> {
        val navbarNavItems1 = mutableListOf(
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

        val navBarComponent1 = NavBarComponent(
            viewModelFactory = BottomNavigationDemoViewModelFactory(
                NavBarComponentDefaults.createNavBarStatePresenter(),
            ),
            content = NavBarComponentDefaults.NavBarComponentView
        )

        val navbarNavItems2 = mutableListOf(
            NavItem(
                label = "Account",
                icon = Icons.Filled.Home,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Settings/Account",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "Profile",
                icon = Icons.Filled.Edit,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Settings/Profile",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            ),
            NavItem(
                label = "About Us",
                icon = Icons.Filled.Email,
                component = TopBarComponent(
                    viewModelFactory = Demo3PageTopBarViewModelFactory(
                        topBarStatePresenter = TopBarComponentDefaults.createTopBarStatePresenter(),
                        screenName = "Settings/About Us",
                        onDone = {}
                    ),
                    content = TopBarComponentDefaults.TopBarComponentView
                )
            )
        )

        val navBarComponent2 = NavBarComponent(
            viewModelFactory = BottomNavigationDemoViewModelFactory(
                NavBarComponentDefaults.createNavBarStatePresenter()
            ),
            content = NavBarComponentDefaults.NavBarComponentView
        )

        return mutableListOf(
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
                component = navBarComponent1.also { it.setNavItems(navbarNavItems1, 0) },
            ),
            NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = navBarComponent2.also { it.setNavItems(navbarNavItems2, 0) },
            )
        )
    }

}
