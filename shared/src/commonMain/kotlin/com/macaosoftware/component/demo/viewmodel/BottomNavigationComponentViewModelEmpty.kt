package com.macaosoftware.component.demo.viewmodel

import com.macaosoftware.component.core.NavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationComponentViewModel
import com.macaosoftware.component.navbar.NavBarComponentDefaults
import com.macaosoftware.component.navbar.NavBarStatePresenterDefault

class BottomNavigationComponentViewModelEmpty(
    bottomNavigationComponent: NavigationComponent,
    override val bottomNavigationStatePresenter: NavBarStatePresenterDefault =
        NavBarComponentDefaults.createNavBarStatePresenter()
) : BottomNavigationComponentViewModel<NavBarStatePresenterDefault>(bottomNavigationComponent) {

    override fun onCreate() {
        println("BottomNavigationComponentDefaultViewModel::onCreate()")
    }

    override fun onStart() {
        println("BottomNavigationComponentDefaultViewModel::onStart()")
    }

    override fun onStop() {
        println("BottomNavigationComponentDefaultViewModel::onStop()")
    }

    override fun onDestroy() {
        println("BottomNavigationComponentDefaultViewModel::onDestroy()")
    }
}
