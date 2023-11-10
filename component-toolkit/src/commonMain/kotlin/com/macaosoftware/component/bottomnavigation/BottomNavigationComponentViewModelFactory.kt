package com.macaosoftware.component.bottomnavigation

interface BottomNavigationComponentViewModelFactory<VM : BottomNavigationComponentViewModel> {
    fun create(bottomNavigationComponent: BottomNavigationComponent<VM>): VM
}
