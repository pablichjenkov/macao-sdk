package com.macaosoftware.component.panel

interface PanelComponentViewModelFactory<T : PanelStatePresenter> {
    fun create(panelComponent: PanelComponent<T>): PanelComponentViewModel<T>
}
