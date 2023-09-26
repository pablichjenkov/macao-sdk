package com.macaosoftware.component.navbar

interface BottomNavigationComponentViewModelFactory<VM : BottomNavigationComponentViewModel> {
    fun create(bottomNavigationComponent: BottomNavigationComponent<VM>): VM
}
