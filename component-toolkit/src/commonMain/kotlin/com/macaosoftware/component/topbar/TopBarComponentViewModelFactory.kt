package com.macaosoftware.component.topbar

interface TopBarComponentViewModelFactory<T : TopBarStatePresenter> {
    fun create(topBarComponent: TopBarComponent<T>): TopBarComponentViewModel<T>
}
