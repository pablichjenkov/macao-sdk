package com.macaosoftware.component.demo.viewmodel.bottomnavigation

import com.macaosoftware.component.bottomnavigation.BottomNavigationComponent
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentDefaults
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentViewModel
import com.macaosoftware.component.bottomnavigation.BottomNavigationStatePresenterDefault

class BottomNavigationDebugViewModel(
    bottomNavigationComponent: BottomNavigationComponent<BottomNavigationDebugViewModel>,
    override val bottomNavigationStatePresenter: BottomNavigationStatePresenterDefault
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
