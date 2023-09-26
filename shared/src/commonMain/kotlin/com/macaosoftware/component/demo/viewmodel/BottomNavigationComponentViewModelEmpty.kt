package com.macaosoftware.component.demo.viewmodel

import com.macaosoftware.component.navbar.BottomNavigationComponentViewModel
import com.macaosoftware.component.navbar.BottomNavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationComponentDefaults
import com.macaosoftware.component.navbar.BottomNavigationStatePresenterDefault

class BottomNavigationComponentViewModelEmpty(
    bottomNavigationComponent: BottomNavigationComponent<BottomNavigationStatePresenterDefault>,
    override val bottomNavigationStatePresenter: BottomNavigationStatePresenterDefault =
        BottomNavigationComponentDefaults.createBottomNavigationStatePresenter()
) : BottomNavigationComponentViewModel<BottomNavigationStatePresenterDefault>(bottomNavigationComponent) {

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
