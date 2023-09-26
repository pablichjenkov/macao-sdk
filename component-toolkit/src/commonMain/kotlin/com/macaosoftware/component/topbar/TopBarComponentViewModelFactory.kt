package com.macaosoftware.component.topbar

interface TopBarComponentViewModelFactory<VM : TopBarComponentViewModel> {
    fun create(topBarComponent: TopBarComponent<VM>): VM
}
