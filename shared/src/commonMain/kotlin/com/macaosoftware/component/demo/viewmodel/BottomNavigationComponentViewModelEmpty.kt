package com.macaosoftware.component.demo.viewmodel

import com.macaosoftware.component.navbar.BottomNavigationComponent
import com.macaosoftware.component.navbar.BottomNavigationComponentDefaults
import com.macaosoftware.component.navbar.BottomNavigationComponentViewModel
import com.macaosoftware.component.navbar.BottomNavigationStatePresenterDefault

class BottomNavigationComponentViewModelEmpty(
    bottomNavigationComponent: BottomNavigationComponent<BottomNavigationComponentViewModelEmpty>,
    override val bottomNavigationStatePresenter: BottomNavigationStatePresenterDefault =
        BottomNavigationComponentDefaults.createBottomNavigationStatePresenter()
) : BottomNavigationComponentViewModel(bottomNavigationComponent) {

    override fun onAttach() {
        println("BottomNavigationComponentDefaultViewModel::onAttach()")
    }

    override fun onStart() {
        println("BottomNavigationComponentDefaultViewModel::onStart()")
    }

    override fun onStop() {
        println("BottomNavigationComponentDefaultViewModel::onStop()")
    }

    override fun onDetach() {
        println("BottomNavigationComponentDefaultViewModel::onDetach()")
    }
}
